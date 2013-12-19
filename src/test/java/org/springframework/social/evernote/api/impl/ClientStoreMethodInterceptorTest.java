package org.springframework.social.evernote.api.impl;

import com.evernote.clients.UserStoreClient;
import com.evernote.edam.type.User;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.social.evernote.api.Impl.ClientStoreMethodInterceptor;
import org.springframework.social.evernote.api.StoreClientHolder;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Tadaya Tsuyukubo
 */
public class ClientStoreMethodInterceptorTest {

	@Test
	public void testInvoke() throws Throwable {

		User user = mock(User.class);

		// underlying store client
		UserStoreClient client = mock(UserStoreClient.class);
		when(client.getUser()).thenReturn(user);

		// invoked interface method
		Method getUserMethod = ReflectionUtils.findMethod(UserStoreClient.class, "getUser");
		MethodInvocation invocation = mock(MethodInvocation.class);
		when(invocation.getThis()).thenReturn(client);
		when(invocation.getMethod()).thenReturn(getUserMethod);
		when(invocation.getArguments()).thenReturn(new Object[]{});

		ClientStoreMethodInterceptor interceptor = new ClientStoreMethodInterceptor();
		Object result = interceptor.invoke(invocation);

		assertThat(result, is(instanceOf(User.class)));
		assertThat((User) result, is(sameInstance(user)));

	}

	@Test
	public void testGetStoreClient() throws Throwable {

		// underlying store client
		UserStoreClient client = mock(UserStoreClient.class);

		// invoked interface method
		Method method = ReflectionUtils.findMethod(StoreClientHolder.class, "getStoreClient");
		MethodInvocation invocation = mock(MethodInvocation.class);
		when(invocation.getThis()).thenReturn(client);
		when(invocation.getMethod()).thenReturn(method);
		when(invocation.getArguments()).thenReturn(new Object[]{});  // dummy value

		ClientStoreMethodInterceptor interceptor = new ClientStoreMethodInterceptor();
		Object result = interceptor.invoke(invocation);

		assertThat(result, is(instanceOf(UserStoreClient.class)));
		assertThat((UserStoreClient) result, is(sameInstance(client)));

	}
}
