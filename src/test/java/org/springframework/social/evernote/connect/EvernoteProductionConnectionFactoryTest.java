package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteProductionConnectionFactoryTest {

	@Test
	public void testCreation() {
		EvernoteProductionConnectionFactory connectionFactory = new EvernoteProductionConnectionFactory("foo", "bar");
		assertThat(connectionFactory.getEvernoteService(), is(EvernoteService.PRODUCTION));
		assertThat(connectionFactory.getProviderId(), is("evernote-production")); // default provider ID
	}
}
