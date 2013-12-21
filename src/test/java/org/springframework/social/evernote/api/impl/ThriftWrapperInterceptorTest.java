package org.springframework.social.evernote.api.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.social.evernote.api.Impl.ThriftWrapperInterceptor;

import static org.mockito.Mockito.*;

/**
 * @author Tadaya Tsuyukubo
 */
public class ThriftWrapperInterceptorTest {

	@Test
	public void testInvocation() throws Throwable {
		MethodInvocation invocation = mock(MethodInvocation.class);
		when(invocation.proceed()).thenReturn(null);

		ThriftWrapperInterceptor interceptor = new ThriftWrapperInterceptor();
		interceptor.invoke(invocation);

		verify(invocation).proceed();
	}
}
