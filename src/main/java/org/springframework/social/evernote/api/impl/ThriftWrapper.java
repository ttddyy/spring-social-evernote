package org.springframework.social.evernote.api.impl;

/**
 * @author Tadaya Tsuyukubo
 */

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author Tadaya Tsuyukubo
 */
public class ThriftWrapper {

	/**
	 * Returns null-safe collection proxy for thrift generated class.
	 *
	 * @param source thrift generated instance
	 * @param <T>
	 * @return a wrapped proxy
	 */
	public static <T> T makeNullSafe(final T source) {

		if (source == null) {
			return null;  // null is null...
		}

		final Class<?> sourceClass = source.getClass();
		final Set<Field> initiallyNullListFields = new HashSet<Field>();
		final Set<Field> initiallyNullSetFields = new HashSet<Field>();
		final Set<Field> initiallyNullMapFields = new HashSet<Field>();

		for (Field field : sourceClass.getDeclaredFields()) {

			if (Modifier.isStatic(field.getModifiers())) {
				continue;  // ignore static attributes
			}

			ReflectionUtils.makeAccessible(field);
			final Object value = ReflectionUtils.getField(field, source);

			// TODO: these can be cached
			final Class<?> fieldType = field.getType();
			final boolean isList = fieldType.isAssignableFrom(List.class);
			final boolean isSet = fieldType.isAssignableFrom(Set.class);
			final boolean isMap = fieldType.isAssignableFrom(Map.class);
			final boolean isCollection = isList || isSet || isMap;

			final boolean isThriftClass = isThriftClass(fieldType);
			if (isThriftClass) {
				if (value != null) {
					// traverse all non-null thrift class attributes
					ReflectionUtils.setField(field, source, makeNullSafe(value));  // recursively call wrap method.
				}
			} else if (isCollection) {
				if (value == null) {
					if (isList) {
						initiallyNullListFields.add(field);
						ReflectionUtils.setField(field, source, new ArrayList<Object>());
					} else if (isSet) {
						initiallyNullSetFields.add(field);
						ReflectionUtils.setField(field, source, new HashSet<Object>());
					} else {
						initiallyNullMapFields.add(field);
						ReflectionUtils.setField(field, source, new HashMap<Object, Object>());
					}
				} else {
					// check values in collection
					if (isList) {
						for (Object o : ((Iterable<?>) value)) {
							Collections.replaceAll((List<Object>) value, o, makeNullSafe(o));
						}
					} else if (isSet) {
						final Set<Object> set = (Set<Object>) value;
						final List<Object> elements = new ArrayList<Object>(set.size());
						for (Object o : ((Iterable<?>) value)) {
							elements.add(makeNullSafe(o));
						}
						set.clear();
						set.addAll(elements);
					}
					// check map values??
				}
			}

		}

		final boolean noNullCollectionField = initiallyNullListFields.isEmpty() &&
				initiallyNullSetFields.isEmpty() && initiallyNullMapFields.isEmpty();
		if (noNullCollectionField) {
			return source;  // doesn't make proxy
		}

		// interceptor that handles thrift write method
		final ThriftNullSafeCollectionInterceptor interceptor = new ThriftNullSafeCollectionInterceptor();
		interceptor.getInitiallyNullListFields().addAll(initiallyNullListFields);
		interceptor.getInitiallyNullSetFields().addAll(initiallyNullSetFields);
		interceptor.getInitiallyNullMapFields().addAll(initiallyNullMapFields);

		// make proxy
		ProxyFactory proxyFactory = new ProxyFactory(source);
		proxyFactory.setProxyTargetClass(true); // aka. use cglib for non-interface source
		proxyFactory.addInterface(ThriftWrapperProxyMarker.class);  // add marker interface
		proxyFactory.addAdvice(interceptor);
		return (T) proxyFactory.getProxy();

	}

	private static boolean isThriftClass(Class<?> clazz) {
		// to work with both in-lined thrift classes and regular generated thrift classes, use name comparison.
		for (Class<?> interfaceClass : ClassUtils.getAllInterfacesForClassAsSet(clazz)) {
			if (interfaceClass.getSimpleName().equals("TBase")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Marker interface for ThriftWrapper created proxy.
	 */
	public static interface ThriftWrapperProxyMarker {
	}

	public static boolean isNullSafeProxy(Object target) {
		return AopUtils.isAopProxy(target) && target instanceof ThriftWrapperProxyMarker;
	}
}