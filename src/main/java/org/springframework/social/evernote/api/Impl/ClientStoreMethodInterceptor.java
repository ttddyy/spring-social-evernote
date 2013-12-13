package org.springframework.social.evernote.api.Impl;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.social.evernote.api.EvernoteExceptionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * Call a corresponding method in underlying ~StoreClient instance from ~Operations interface
 *
 * @author Tadaya Tsuyukubo
 */
public class ClientStoreMethodInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		// TODO: handle StoreClientHolder interface method

		Object target = invocation.getThis();  // target client store instance
		Class<?> targetClass = target.getClass();
		Method invokedMethod = invocation.getMethod();  // invoked method in ~Operations

		// From invoked method in ~Operations, find corresponding method in actual ~StoreClient class
		Method targetMethod = AopUtils.getMostSpecificMethod(invokedMethod, targetClass);

		ReflectionUtils.makeAccessible(targetMethod);

		try {
			return ReflectionUtils.invokeMethod(targetMethod, target, invocation.getArguments());
		} catch (Exception e) {
			// retrieve original exception.
			// ReflectionUtils wraps checked exception to UndeclaredThrowableException.
			Throwable original = getOriginalException(e);
			throw EvernoteExceptionUtils.convert((Exception) original);
		}

	}

	private static Throwable getOriginalException(Throwable e) {
		Throwable original = e;
		if (e instanceof UndeclaredThrowableException) {
			original = getOriginalException(e.getCause());
		}
		return original;
	}

}
