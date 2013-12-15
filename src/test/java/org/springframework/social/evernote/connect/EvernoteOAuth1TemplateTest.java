package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.junit.Test;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteOAuth1TemplateTest {


	@Test
	public void testBuildAuthenticateUrl() {
		EvernoteOAuth1Template template = new EvernoteOAuth1Template("key", "secret", EvernoteService.SANDBOX);
		String authenticateUrl = template.buildAuthenticateUrl("abc", new OAuth1Parameters());
		assertThat(authenticateUrl, is(EvernoteService.SANDBOX.getAuthorizationUrl("abc")));
	}

	@Test
	public void testBuildAuthorizeUrl() {
		EvernoteOAuth1Template template = new EvernoteOAuth1Template("key", "secret", EvernoteService.SANDBOX);
		String authorizeUrl = template.buildAuthorizeUrl("abc", new OAuth1Parameters());
		assertThat(authorizeUrl, is(EvernoteService.SANDBOX.getAuthorizationUrl("abc")));
	}

	@Test
	public void testCreateOAuthToken() {

		EvernoteOAuth1Template template = new EvernoteOAuth1Template("key", "secret", EvernoteService.SANDBOX);
		MultiValueMap<String, String> response = new OAuth1Parameters();
		response.add("oauth_token", "test_token");
		response.add("oauth_token_secret", "test_secret");
		response.add("edam_shard", "test_shard");
		response.add("edam_userId", "test_userId");
		response.add("edam_expires", "test_expires");
		response.add("edam_noteStoreUrl", "test_noteStoreUrl");
		response.add("edam_webApiUrlPrefix", "test_webApiUrlPrefix");

		OAuthToken token = template.createOAuthToken("tokenValue", "tokenSecret", response);
		assertThat(token, instanceOf(EvernoteOAuthToken.class));

		EvernoteOAuthToken eToken = (EvernoteOAuthToken) token;
		assertThat(eToken.getValue(), is("test_token"));
		assertThat(eToken.getSecret(), is("test_secret"));
		assertThat(eToken.getEdamShard(), is("test_shard"));
		assertThat(eToken.getEdamUserId(), is("test_userId"));
		assertThat(eToken.getEdamExpires(), is("test_expires"));
		assertThat(eToken.getEdamNoteStoreUrl(), is("test_noteStoreUrl"));
		assertThat(eToken.getEdamWebApiUrlPrefix(), is("test_webApiUrlPrefix"));
	}

}
