package org.springframework.social.evernote.api.Impl;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Proxy interceptor that wraps a returned thrift object to have null-safe-collections.
 *
 * @author Tadaya Tsuyukubo
 */
public class NullSafeThriftCollectionInterceptor implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return ThriftWrapper.makeNullSafe(invocation.proceed());
	}
}
