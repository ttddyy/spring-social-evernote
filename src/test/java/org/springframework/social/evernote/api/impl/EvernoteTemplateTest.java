package org.springframework.social.evernote.api.impl;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import org.junit.Test;
import org.springframework.social.evernote.connect.EvernoteOAuthToken;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteTemplateTest {

	@Test
	public void testConstructorWithStringAccessToken() {
		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, "TOKEN");
		assertThat(template.getEvernoteService(), is(EvernoteService.SANDBOX));
		assertThat(template.getEvernoteAuth(), is(notNullValue()));
		assertThat(template.getEvernoteAuth().getToken(), is("TOKEN"));
		assertThat(template.getEvernoteAuth().getNoteStoreUrl(), is(nullValue()));
		assertThat(template.getEvernoteAuth().getWebApiUrlPrefix(), is(nullValue()));
		assertThat(template.getEvernoteAuth().getUserId(), is(0));

		EvernoteService service = retrieveServiceField(template.getEvernoteAuth());
		assertThat(service, is(notNullValue()));
		assertThat(service, is(sameInstance(EvernoteService.SANDBOX)));
	}

	@Test
	public void testConstructorWithEvernoteOAuthToken() {
		EvernoteOAuthToken token = new EvernoteOAuthToken.EvernoteOAuthTokenBuilder()
				.setToken("TOKEN")
				.setEdamNoteStoreUrl("NOTE_STORE_URL")
				.setEdamWebApiUrlPrefix("WEB_API_URL_PREFIX")
				.setEdamUserId("100")
				.build();

		EvernoteTemplate template = new EvernoteTemplate(EvernoteService.SANDBOX, token);
		assertThat(template.getEvernoteService(), is(EvernoteService.SANDBOX));
		assertThat(template.getEvernoteAuth(), is(notNullValue()));
		assertThat(template.getEvernoteAuth().getToken(), is("TOKEN"));
		assertThat(template.getEvernoteAuth().getNoteStoreUrl(), is("NOTE_STORE_URL"));
		assertThat(template.getEvernoteAuth().getWebApiUrlPrefix(), is("WEB_API_URL_PREFIX"));
		assertThat(template.getEvernoteAuth().getUserId(), is(100));

		EvernoteService service = retrieveServiceField(template.getEvernoteAuth());
		assertThat(service, is(notNullValue()));
		assertThat(service, is(sameInstance(EvernoteService.SANDBOX)));
	}

	private EvernoteService retrieveServiceField(EvernoteAuth evernoteAuth) {
		// can be replaced with ReflectionTestUtils if spring-test is included
		Field serviceField = ReflectionUtils.findField(EvernoteAuth.class, "service");
		ReflectionUtils.makeAccessible(serviceField);
		return (EvernoteService) ReflectionUtils.getField(serviceField, evernoteAuth);
	}

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
