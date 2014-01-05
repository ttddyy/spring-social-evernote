package org.springframework.social.evernote.api.impl;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteTemplateTest {

	@Test
	public void testGetEvernoteAuth() {
		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "access_token");
		EvernoteAuth auth = template.getEvernoteAuth();
		assertThat(auth, is(notNullValue()));
		assertThat(auth.getToken(), is("access_token"));
	}

	@Test
	public void testGetEvernoteService() {
		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "access_token");
		assertThat(template.getEvernoteService(), is(sameInstance(EvernoteService.SANDBOX)));

		template = new EvernoteTemplate(EvernoteService.PRODUCTION, "access_token");
		assertThat(template.getEvernoteService(), is(sameInstance(EvernoteService.PRODUCTION)));
	}

}
