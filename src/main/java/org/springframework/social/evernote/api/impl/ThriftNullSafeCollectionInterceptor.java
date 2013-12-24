package org.springframework.social.evernote.api.impl;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Tadaya Tsuyukubo
 */
public class ThriftNullSafeCollectionInterceptor implements MethodInterceptor {

	private Set<Field> initiallyNullListFields = new HashSet<Field>();
	private Set<Field> initiallyNullSetFields = new HashSet<Field>();
	private Set<Field> initiallyNullMapFields = new HashSet<Field>();

	public ThriftNullSafeCollectionInterceptor() {
	}

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

	public Set<Field> getInitiallyNullListFields() {
		return initiallyNullListFields;
	}

	public Set<Field> getInitiallyNullSetFields() {
		return initiallyNullSetFields;
	}

	public Set<Field> getInitiallyNullMapFields() {
		return initiallyNullMapFields;
	}
}
