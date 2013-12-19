package org.springframework.social.evernote.api;

import com.evernote.clients.BusinessNoteStoreClient;
import com.evernote.clients.LinkedNoteStoreClient;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.Factory;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.social.evernote.api.Impl.ClientStoreMethodInterceptor;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Tadaya Tsuyukubo
 */
@RunWith(Parameterized.class)
public class StoreOperationsActualInvocationTest {

	private Method operationMethod;
	private Class<?> implClass;

	@Parameters
	public static Collection<Object[]> data() throws Exception {
		List<Object[]> list = new ArrayList<Object[]>();
		list.addAll(getTestParameters(UserStoreOperations.class, UserStoreClient.class));
		list.addAll(getTestParameters(NoteStoreOperations.class, NoteStoreClient.class));
		list.addAll(getTestParameters(LinkedNoteStoreOperations.class, LinkedNoteStoreClient.class));
		list.addAll(getTestParameters(BusinessNoteStoreOperations.class, BusinessNoteStoreClient.class));
		return list;
	}

	private static List<Object[]> getTestParameters(Class<?> operationClass, Class<?> implClass) {
		Method[] methods = operationClass.getDeclaredMethods();
		// sort operationMethod by name. just for always run in same order.
		Arrays.sort(methods, new Comparator<Method>() {
			@Override
			public int compare(Method left, Method right) {
				return left.getName().compareTo(right.getName());
			}
		});
		List<Object[]> list = new ArrayList<Object[]>(methods.length);
		for (Method method : methods) {
			list.add(new Object[]{method, implClass});
		}
		return list;
	}

	public StoreOperationsActualInvocationTest(Method operationMethod, Class<?> implClass) {
		this.operationMethod = operationMethod;
		this.implClass = implClass;
	}

	/**
	 * Check invocation of ~Operation calls actual operationMethod.
	 */
	@Test
	public void testActualInvocation() throws Throwable {
		// check each operationMethod in operations interface actually invokes underlying object.
		InvocationReporter invocationReporter = new InvocationReporter();

		// create a proxy that pretends underlying object(~ClientStore).
		// the proxy works as a stub and holds what operationMethod has invoked for verification.
		Object client = createUnderlyingObjectProxy(invocationReporter);

		// need fake arguments to invoke underlying instance.
		Object[] fakeParams = getFakeParameters(operationMethod);

		MethodInvocation invocation = mock(MethodInvocation.class);
		when(invocation.getThis()).thenReturn(client);
		when(invocation.getMethod()).thenReturn(operationMethod);
		when(invocation.getArguments()).thenReturn(fakeParams);

		// invoke operations class operationMethod
		ClientStoreMethodInterceptor interceptor = new ClientStoreMethodInterceptor();
		interceptor.invoke(invocation);

		// verify
		assertThat(invocationReporter.isInvoked(), is(true));  // underlying instance has invoked.
		assertThat(invocationReporter.getInvokedMethod().getName(), is(operationMethod.getName())); // should invoke same name operationMethod
		assertThat(invocationReporter.getInvokedArguments().length, is(fakeParams.length));  // args must be passed down
		Object[] args = invocationReporter.getInvokedArguments();
		for (int i = 0; i < args.length; i++) {
			assertEquals(fakeParams[i].getClass(), args[i].getClass());
		}

	}

	private Object createUnderlyingObjectProxy(InvocationReporter invocationReporter) {
		// use Cglib Enhancer inlined in spring3.2
		// spring3.2's ProxyFactory cannot creates a proxy with constructor arguments.
		// CglibAopProxy is package scoped and cannot call "setConstructorArguments" operationMethod.
		// This is fixed in spring4 with ObjenesisCglibAopProxy: https://jira.springsource.org/browse/SPR-3150
		// for now simulate what spring4 does with cglib and objenesis.
		final Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(implClass);
		enhancer.setInterfaces(implClass.getInterfaces());

		Objenesis objenesis = new ObjenesisStd();
		Callback[] callbacks = new Callback[]{invocationReporter};
		Class<?>[] types = new Class[callbacks.length];
		for (int x = 0; x < types.length; x++) {
			types[x] = callbacks[x].getClass();
		}
		enhancer.setCallbackTypes(types);

		Object client = objenesis.newInstance(enhancer.createClass());
		((Factory) client).setCallbacks(callbacks);
		return client;
	}

	private Object[] getFakeParameters(Method method) {
		Objenesis objenesis = new ObjenesisStd();
		Class<?>[] paramTypes = method.getParameterTypes();
		Object[] fakeParams = new Object[paramTypes.length];
		for (int i = 0; i < paramTypes.length; i++) {
			Class<?> paramType = paramTypes[i];

			// TODO: better impl.
			Object fakeParam;
			if (ClassUtils.isPrimitiveArray(paramType)) {
				// can objenesis handle primitive array? for now, create just empty array
				fakeParam = Array.newInstance(paramType.getComponentType(), 0);
			} else {
				if (paramType.isPrimitive()) {
					paramType = ClassUtils.resolvePrimitiveIfNecessary(paramType);
				} else if (paramType.isInterface()) {
					if (paramType.isAssignableFrom(List.class)) {
						paramType = ArrayList.class;
					}
				}
				fakeParam = objenesis.newInstance(paramType);
			}
			fakeParams[i] = fakeParam;
		}

		return fakeParams;
	}

	/**
	 * stub to hold invoked method and params.
	 */
	private static class InvocationReporter implements org.springframework.cglib.proxy.MethodInterceptor {
		private boolean invoked;
		private Method invokedMethod;
		private Object[] invokedArguments;

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			this.invoked = true;
			this.invokedMethod = method;
			this.invokedArguments = args;
			return null;  // don't invoke actual method, and just return null
		}

		public boolean isInvoked() {
			return invoked;
		}

		public Method getInvokedMethod() {
			return invokedMethod;
		}

		public Object[] getInvokedArguments() {
			return invokedArguments;
		}
	}

}


