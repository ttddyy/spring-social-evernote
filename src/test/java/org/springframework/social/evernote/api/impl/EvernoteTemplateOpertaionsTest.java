package org.springframework.social.evernote.api.impl;

import com.evernote.auth.EvernoteService;
import com.evernote.clients.BusinessNoteStoreClient;
import com.evernote.clients.LinkedNoteStoreClient;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.type.LinkedNotebook;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.social.evernote.api.BusinessNoteStoreOperations;
import org.springframework.social.evernote.api.Impl.ClientStoreMethodInterceptor;
import org.springframework.social.evernote.api.Impl.EvernoteTemplate;
import org.springframework.social.evernote.api.Impl.ThriftWrapperInterceptor;
import org.springframework.social.evernote.api.LinkedNoteStoreOperations;
import org.springframework.social.evernote.api.NoteStoreOperations;
import org.springframework.social.evernote.api.UserStoreOperations;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tetst for ~ClientStoreOperations methods.
 *
 * @author Tadaya Tsuyukubo
 */
public class EvernoteTemplateOpertaionsTest {

	@Test
	public void testBusinessNoteStoreClient() throws Exception {
		BusinessNoteStoreClient businessNoteStoreClient = mock(BusinessNoteStoreClient.class);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		EvernoteTemplate spy = spy(template);  // partial mock
		doReturn(businessNoteStoreClient).when(spy).businessNoteStoreClient();

		BusinessNoteStoreOperations result = spy.businessNoteStoreOperations();
		assertThat(AopUtils.isAopProxy(result), is(true));
		Advisor[] advisors = ((Advised) result).getAdvisors();
		assertThat(advisors, arrayWithSize(2));  // order is important
		assertThat(advisors[0].getAdvice(), is(instanceOf(ThriftWrapperInterceptor.class)));
		assertThat(advisors[1].getAdvice(), is(instanceOf(ClientStoreMethodInterceptor.class)));
	}

	@Test
	public void testLinkedNoteStoreClientOperations() throws Exception {
		LinkedNoteStoreClient linkedNoteStoreClient = mock(LinkedNoteStoreClient.class);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		EvernoteTemplate spy = spy(template);  // partial mock
		doReturn(linkedNoteStoreClient).when(spy).linkedNoteStoreClient(argThat(any(LinkedNotebook.class)));

		LinkedNoteStoreOperations result = spy.linkedNoteStoreOperations(mock(LinkedNotebook.class));
		assertThat(AopUtils.isAopProxy(result), is(true));
		Advisor[] advisors = ((Advised) result).getAdvisors();
		assertThat(advisors, arrayWithSize(2));  // order is important
		assertThat(advisors[0].getAdvice(), is(instanceOf(ThriftWrapperInterceptor.class)));
		assertThat(advisors[1].getAdvice(), is(instanceOf(ClientStoreMethodInterceptor.class)));
	}

	@Test
	public void testNoteStoreClient() throws Exception {
		NoteStoreClient noteStoreClient = mock(NoteStoreClient.class);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		EvernoteTemplate spy = spy(template);  // partial mock
		doReturn(noteStoreClient).when(spy).noteStoreClient();

		NoteStoreOperations result = spy.noteStoreOperations();
		assertThat(AopUtils.isAopProxy(result), is(true));
		Advisor[] advisors = ((Advised) result).getAdvisors();
		assertThat(advisors, arrayWithSize(2));  // order is important
		assertThat(advisors[0].getAdvice(), is(instanceOf(ThriftWrapperInterceptor.class)));
		assertThat(advisors[1].getAdvice(), is(instanceOf(ClientStoreMethodInterceptor.class)));
	}

	@Test
	public void testUserStoreClient() throws Exception {
		UserStoreClient userStoreClient = mock(UserStoreClient.class);

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "token");
		EvernoteTemplate spy = spy(template);  // partial mock
		doReturn(userStoreClient).when(spy).userStoreClient();

		UserStoreOperations result = spy.userStoreOperations();
		assertThat(AopUtils.isAopProxy(result), is(true));
		Advisor[] advisors = ((Advised) result).getAdvisors();
		assertThat(advisors, arrayWithSize(2));  // order is important
		assertThat(advisors[0].getAdvice(), is(instanceOf(ThriftWrapperInterceptor.class)));
		assertThat(advisors[1].getAdvice(), is(instanceOf(ClientStoreMethodInterceptor.class)));
	}

}
