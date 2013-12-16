package org.springframework.social.evernote.api;

import com.evernote.clients.BusinessNoteStoreClient;
import com.evernote.clients.LinkedNoteStoreClient;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.*;
import static org.junit.runners.Parameterized.Parameters;

/**
 * @author Tadaya Tsuyukubo
 */
@RunWith(Parameterized.class)
public class StoreOperationsTest {

	private Class<?> operationClass;
	private Class<?> implClass;
	private List<String> ignoreMethodNames;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				// ~StoreOperation interface, actual impl class
				{UserStoreOperations.class, UserStoreClient.class, Collections.emptyList()},
				{NoteStoreOperations.class, NoteStoreClient.class, Arrays.asList("getClient")},
				{LinkedNoteStoreOperations.class, LinkedNoteStoreClient.class, Arrays.asList("getClient")},
				{BusinessNoteStoreOperations.class, BusinessNoteStoreClient.class, Collections.emptyList()},
		});
	}

	public StoreOperationsTest(Class<?> operationClass, Class<?> implClass, List<String> ignoreMethodNames) {
		this.operationClass = operationClass;
		this.implClass = implClass;
		this.ignoreMethodNames = ignoreMethodNames;
	}

	/**
	 * Check whether the ~Operations class implemented corresponding methods in ~ClientStore class.
	 */
	@Test
	public void testMethods() throws Exception {
		// operations class needs to define all methods in impl class
		Method[] implMethods = implClass.getDeclaredMethods();

		// first check method names and parameter
		Set<String> notImplementedMethodNames = new HashSet<String>();
		List<Method> implementedMethods = new ArrayList<Method>();
		for (Method implMethod : implMethods) {

			if (!Modifier.isPublic(implMethod.getModifiers()) || ignoreMethodNames.contains(implMethod.getName())) {
				continue; // ignore non-public methods
			}

			try {
				operationClass.getMethod(implMethod.getName(), implMethod.getParameterTypes());
			} catch (NoSuchMethodException e) {
				notImplementedMethodNames.add(implMethod.getName());
			}
			implementedMethods.add(implMethod);
		}

		if (!notImplementedMethodNames.isEmpty()) {
			fail(String.format("%s is not declaring Corresponding method: %s", operationClass, notImplementedMethodNames));
		}

		// check the exception class
		for (Method implMethod : implementedMethods) {
			Method targetMethod = operationClass.getMethod(implMethod.getName(), implMethod.getParameterTypes());
			Class<?>[] exceptions = targetMethod.getExceptionTypes();
			assertThat(exceptions, arrayWithSize(1));
			assertEquals(EvernoteException.class, exceptions[0]);
		}
	}

	// TODO: test when impl method throws exception

}


