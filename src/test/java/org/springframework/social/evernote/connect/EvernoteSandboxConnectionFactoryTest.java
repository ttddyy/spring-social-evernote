package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteSandboxConnectionFactoryTest {

	@Test
	public void testCreation() {
		EvernoteSandboxConnectionFactory connectionFactory = new EvernoteSandboxConnectionFactory("foo", "bar");
		assertThat(connectionFactory.getEvernoteService(), is(EvernoteService.SANDBOX));
		assertThat(connectionFactory.getProviderId(), is("evernote-sandbox")); // default provider ID
	}
}
