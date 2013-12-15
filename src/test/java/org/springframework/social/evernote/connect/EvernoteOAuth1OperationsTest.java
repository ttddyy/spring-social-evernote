package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.junit.Test;
import org.springframework.social.oauth1.OAuth1Operations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteOAuth1OperationsTest {

	@Test
	public void testDefaultService() {
		EvernoteOAuth1Operations operations = new EvernoteOAuth1Operations("foo", "bar");
		OAuth1Operations selected = operations.getSelectedOauth1Operations();
		assertThat(selected, instanceOf(EvernoteOAuth1Template.class));
		EvernoteService service = ((EvernoteOAuth1Template) selected).getEvernoteService();
		assertThat(service, is(EvernoteService.SANDBOX));
	}

}
