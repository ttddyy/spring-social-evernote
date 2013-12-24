package org.springframework.social.evernote.api.impl;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.social.evernote.api.EvernoteException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.runners.Parameterized.Parameters;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Tadaya Tsuyukubo
 */
@RunWith(Parameterized.class)
public class ClientStoreMethodInterceptorWithExceptionTest {

	private Exception original;
	private Exception expected;

	@Parameters
	public static Collection<Object[]> data() {
		EDAMUserException edamUserException = new EDAMUserException();
		EDAMSystemException edamSystemException = new EDAMSystemException();
		EDAMNotFoundException edamNotFoundException = new EDAMNotFoundException();
		RuntimeException runtimeException = new RuntimeException();

		return Arrays.asList(new Object[][]{
				// original, expected
				{edamUserException, edamUserException},
				{edamSystemException, edamSystemException},
				{edamNotFoundException, edamNotFoundException},
				{runtimeException, runtimeException}
		});
	}

	public ClientStoreMethodInterceptorWithExceptionTest(Exception original, Exception expected) {
		this.original = original;
		this.expected = expected;
	}

	@Test
	public void testInvokeWithException() throws Throwable {

		// underlying class
		FooImpl fooImpl = mock(FooImpl.class);
		when(fooImpl.echo()).thenThrow(original);

		// invoked interface method
		Method getUserMethod = ReflectionUtils.findMethod(Foo.class, "echo");
		MethodInvocation invocation = mock(MethodInvocation.class);
		when(invocation.getThis()).thenReturn(fooImpl);
		when(invocation.getMethod()).thenReturn(getUserMethod);
		when(invocation.getArguments()).thenReturn(new Object[]{});

		ClientStoreMethodInterceptor interceptor = new ClientStoreMethodInterceptor();
		try {
			interceptor.invoke(invocation);
			fail("didn't throw exception");
		} catch (Throwable throwable) {
			assertThat(throwable, is(instanceOf(EvernoteException.class)));
			EvernoteException exception = (EvernoteException) throwable;
			assertThat(exception.getCause(), is(instanceOf(Exception.class)));
			assertThat((Exception) exception.getCause(), is(sameInstance(expected)));
		}
	}

	public interface Foo {
		String echo() throws EvernoteException;
	}

	public class FooImpl {
		String echo() throws EDAMUserException, EDAMSystemException, EDAMNotFoundException, TException {
			return "foo";
		}
	}
}
