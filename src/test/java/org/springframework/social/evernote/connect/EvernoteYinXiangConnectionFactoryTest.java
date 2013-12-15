package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteYinXiangConnectionFactoryTest {

	@Test
	public void testCreation() {
		EvernoteYinXiangConnectionFactory connectionFactory = new EvernoteYinXiangConnectionFactory("foo", "bar");
		assertThat(connectionFactory.getEvernoteService(), is(EvernoteService.YINXIANG));
		assertThat(connectionFactory.getProviderId(), is("evernote-yinxiang")); // default provider ID
	}
}
