package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

/**
 * @author Tadaya Tsuyukubo
 */
@RunWith(Parameterized.class)
public class EvernoteOAuth1OperationsByServiceTest {

	private EvernoteService evernoteService;

	public EvernoteOAuth1OperationsByServiceTest(EvernoteService evernoteService) {
		this.evernoteService = evernoteService;
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{EvernoteService.SANDBOX},
				{EvernoteService.PRODUCTION},
				{EvernoteService.YINXIANG},
		});
	}

	@Test
	public void testSelectedService() {
		EvernoteOAuth1Operations operations = new EvernoteOAuth1Operations("foo", "bar", evernoteService);
		OAuth1Operations selected = operations.getSelectedOauth1Operations();
		EvernoteService service = ((EvernoteOAuth1Template) selected).getEvernoteService();
		assertThat(service, is(evernoteService));
	}

	@Test
	public void testAuthUrlByServiceEnvironment() {
		EvernoteOAuth1Operations operations = new EvernoteOAuth1Operations("foo", "bar", evernoteService);
		String url = operations.buildAuthenticateUrl("abc", new OAuth1Parameters());
		assertThat(url, is(evernoteService.getAuthorizationUrl("abc")));
	}

}
