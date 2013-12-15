package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteConnectionFactoryTest {

	@Test
	public void testCreation() {
		EvernoteConnectionFactory connectionFactory = new EvernoteConnectionFactory("foo", "bar");
		assertThat(connectionFactory.getEvernoteService(), is(EvernoteService.SANDBOX));
		assertThat(connectionFactory.getProviderId(), is("evernote")); // default provider ID
		assertThat(connectionFactory.getOAuthOperations(), instanceOf(EvernoteOAuth1Operations.class));
	}

	@Test
	public void testCreationWithService() {
		EvernoteConnectionFactory connectionFactory = new EvernoteConnectionFactory("foo", "bar", EvernoteService.PRODUCTION);
		assertThat(connectionFactory.getEvernoteService(), is(EvernoteService.PRODUCTION));
		assertThat(connectionFactory.getProviderId(), is("evernote")); // default provider ID
		assertThat(connectionFactory.getOAuthOperations(), instanceOf(EvernoteOAuth1Operations.class));
	}

	@Test
	public void testCreationWithServiceAndProviderId() {
		EvernoteConnectionFactory connectionFactory = new EvernoteConnectionFactory("foo", "bar", EvernoteService.PRODUCTION, "baz");
		assertThat(connectionFactory.getEvernoteService(), is(EvernoteService.PRODUCTION));
		assertThat(connectionFactory.getProviderId(), is("baz"));
		assertThat(connectionFactory.getOAuthOperations(), instanceOf(EvernoteOAuth1Operations.class));
	}
}
