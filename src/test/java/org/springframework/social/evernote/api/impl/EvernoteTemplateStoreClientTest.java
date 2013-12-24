package org.springframework.social.evernote.api.impl;

import com.evernote.auth.EvernoteService;
import com.evernote.clients.*;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.type.LinkedNotebook;
import com.evernote.thrift.transport.TTransportException;
import org.junit.Test;
import org.springframework.social.evernote.api.EvernoteException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteTemplateStoreClientTest {

	@Test
	public void testBusinessNoteStoreClient() throws Exception {
		BusinessNoteStoreClient businessNoteStoreClient = mock(BusinessNoteStoreClient.class);
		ClientFactory clientFactory = mock(ClientFactory.class);
		when(clientFactory.createBusinessNoteStoreClient()).thenReturn(businessNoteStoreClient);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		replaceClientFactory(template, clientFactory);

		BusinessNoteStoreClient result = template.businessNoteStoreClient();
		assertThat(result, is(sameInstance(businessNoteStoreClient)));
	}

	@Test
	public void testBusinessNoteStoreClientWithException() throws Exception {
		EDAMUserException exception = new EDAMUserException();
		ClientFactory clientFactory = mock(ClientFactory.class);
		when(clientFactory.createBusinessNoteStoreClient()).thenThrow(exception);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		replaceClientFactory(template, clientFactory);

		try {
			template.businessNoteStoreClient();
			fail("didn't thrown exception");
		} catch (EvernoteException e) {
			Throwable cause = e.getCause();
			assertThat(cause, is(instanceOf(EDAMUserException.class)));
			assertThat((EDAMUserException) cause, is(sameInstance(exception)));
		}
	}

	@Test
	public void testLinkedNoteStoreClient() throws Exception {
		LinkedNoteStoreClient linkedNoteStoreClient = mock(LinkedNoteStoreClient.class);
		ClientFactory clientFactory = mock(ClientFactory.class);
		when(clientFactory.createLinkedNoteStoreClient(argThat(any(LinkedNotebook.class)))).thenReturn(linkedNoteStoreClient);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		replaceClientFactory(template, clientFactory);

		LinkedNoteStoreClient result = template.linkedNoteStoreClient(mock(LinkedNotebook.class));
		assertThat(result, is(sameInstance(linkedNoteStoreClient)));
	}

	@Test
	public void testLinkedNoteStoreClientWithException() throws Exception {
		EDAMUserException exception = new EDAMUserException();
		ClientFactory clientFactory = mock(ClientFactory.class);
		when(clientFactory.createLinkedNoteStoreClient(argThat(any(LinkedNotebook.class)))).thenThrow(exception);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		replaceClientFactory(template, clientFactory);

		try {
			template.linkedNoteStoreClient(mock(LinkedNotebook.class));
			fail("didn't thrown exception");
		} catch (EvernoteException e) {
			Throwable cause = e.getCause();
			assertThat(cause, is(instanceOf(EDAMUserException.class)));
			assertThat((EDAMUserException) cause, is(sameInstance(exception)));
		}
	}

	@Test
	public void testNoteStoreClient() throws Exception {
		NoteStoreClient noteStoreClient = mock(NoteStoreClient.class);
		ClientFactory clientFactory = mock(ClientFactory.class);
		when(clientFactory.createNoteStoreClient()).thenReturn(noteStoreClient);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		replaceClientFactory(template, clientFactory);

		NoteStoreClient result = template.noteStoreClient();
		assertThat(result, is(sameInstance(noteStoreClient)));
	}

	@Test
	public void testNoteStoreClientWithException() throws Exception {
		EDAMUserException exception = new EDAMUserException();
		ClientFactory clientFactory = mock(ClientFactory.class);
		when(clientFactory.createNoteStoreClient()).thenThrow(exception);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		replaceClientFactory(template, clientFactory);

		try {
			template.noteStoreClient();
			fail("didn't thrown exception");
		} catch (EvernoteException e) {
			Throwable cause = e.getCause();
			assertThat(cause, is(instanceOf(EDAMUserException.class)));
			assertThat((EDAMUserException) cause, is(sameInstance(exception)));
		}
	}


	@Test
	public void testUserStoreClient() throws Exception {
		UserStoreClient userStoreClient = mock(UserStoreClient.class);
		ClientFactory clientFactory = mock(ClientFactory.class);
		when(clientFactory.createUserStoreClient()).thenReturn(userStoreClient);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		replaceClientFactory(template, clientFactory);

		UserStoreClient result = template.userStoreClient();
		assertThat(result, is(sameInstance(userStoreClient)));
	}

	@Test
	public void testUserStoreClientWithException() throws Exception {
		TTransportException exception = new TTransportException();
		ClientFactory clientFactory = mock(ClientFactory.class);
		when(clientFactory.createUserStoreClient()).thenThrow(exception);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		replaceClientFactory(template, clientFactory);

		try {
			template.userStoreClient();
			fail("didn't thrown exception");
		} catch (EvernoteException e) {
			Throwable cause = e.getCause();
			assertThat(cause, is(instanceOf(TTransportException.class)));
			assertThat((TTransportException) cause, is(sameInstance(exception)));
		}
	}


	private void replaceClientFactory(final EvernoteTemplate template, final ClientFactory clientFactory) {
		ReflectionUtils.doWithFields(EvernoteTemplate.class, new ReflectionUtils.FieldCallback() {
					@Override
					public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
						field.setAccessible(true);
						field.set(template, clientFactory);
					}
				}, new ReflectionUtils.FieldFilter() {
					@Override
					public boolean matches(Field field) {
						return field.getType().isAssignableFrom(ClientFactory.class);
					}
				}
		);
	}
}
