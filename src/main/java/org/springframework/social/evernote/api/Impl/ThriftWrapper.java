package org.springframework.social.evernote.api.Impl;

/**
 * @author Tadaya Tsuyukubo
 */

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

		boolean createProxy = false;
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
						createProxy = true;
					} else if (isSet) {
						initiallyNullSetFields.add(field);
						ReflectionUtils.setField(field, source, new HashSet<Object>());
						createProxy = true;
					} else {
						initiallyNullMapFields.add(field);
						ReflectionUtils.setField(field, source, new HashMap<Object, Object>());
						createProxy = true;
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

		if (!createProxy) {
			return source;  // doesn't make proxy
		}

		// make proxy
		ProxyFactory proxyFactory = new ProxyFactory(source);
		proxyFactory.setProxyTargetClass(true); // aka. use cglib for non-interface source
		proxyFactory.addAdvice(new org.aopalliance.intercept.MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				final Method method = invocation.getMethod();
				final Object invoked = invocation.getThis();
				if (isThriftWriteMethod(method)) {
					// when collection was initially null and still is an empty collection, then set back to null
					// to omit thrift transmission.
					for (Field field : initiallyNullListFields) {
						if (((List) ReflectionUtils.getField(field, invoked)).size() == 0) {
							ReflectionUtils.setField(field, invoked, null);
						}
					}
					for (Field field : initiallyNullSetFields) {
						if (((Set) ReflectionUtils.getField(field, invoked)).size() == 0) {
							ReflectionUtils.setField(field, invoked, null);
						}
					}
					for (Field field : initiallyNullMapFields) {
						if (((Map) ReflectionUtils.getField(field, invoked)).size() == 0) {
							ReflectionUtils.setField(field, invoked, null);
						}
					}
				}
				return invocation.proceed();
			}
		});
		return (T) proxyFactory.getProxy();

	}

	private static boolean isThriftWriteMethod(Method method) {
		// to work with in-lined thrift classes (com.evernote.thrift), and regular thrift generated classes in unittest
		// compare by method name and parameter name.
		if ("write".equals(method.getName())) {
			Class<?>[] paramTypes = method.getParameterTypes();
			if (paramTypes.length == 1 && "TProtocol".equals(paramTypes[0].getSimpleName())) {
				return true;
			}
		}
		return false;
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


}