package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.springframework.social.oauth1.OAuth1Template;
import org.springframework.social.oauth1.OAuth1Version;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteOAuth1Template extends OAuth1Template {

	private EvernoteService evernoteService;

	public EvernoteOAuth1Template(String consumerKey, String consumerSecret, EvernoteService evernoteService) {
		super(consumerKey, consumerSecret,
				evernoteService.getRequestTokenEndpoint(),  // https://evernoteHost/oauth
				getAuthorizationUrl(evernoteService),  // https://evernoteHost/OAuth.action
				evernoteService.getAccessTokenEndpoint()  // https://evernoteHost/oauth
		);
		this.evernoteService = evernoteService;
	}

	private static String getAuthorizationUrl(EvernoteService evernoteService) {
		// TODO: improve logic
		String url = evernoteService.getAuthorizationUrl("");
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build();
		url = url.substring(0, url.lastIndexOf(uriComponents.getQuery()));
		if (url.endsWith("?")) {
			url = url.substring(0, url.length() - 1); // chop
		}
		return url;
	}

	@Override
	protected OAuthToken createOAuthToken(String tokenValue, String tokenSecret, MultiValueMap<String, String> response) {
		return new EvernoteOAuthToken.EvernoteOAuthTokenBuilder()
				.setToken(response.getFirst(EvernoteOAuthToken.KEY_OAUTH_TOKEN))
				.setSecret(response.getFirst(EvernoteOAuthToken.KEY_OAUTH_TOKEN_SECRET))
				.setEdamShard(response.getFirst(EvernoteOAuthToken.EDAM_SHARD))
				.setEdamUserId(response.getFirst(EvernoteOAuthToken.EDAM_USER_ID))
				.setEdamExpires(response.getFirst(EvernoteOAuthToken.EDAM_EXPIRES))
				.setEdamNoteStoreUrl(response.getFirst(EvernoteOAuthToken.EDAM_NOTE_STORE_URL))
				.setEdamWebApiUrlPrefix(response.getFirst(EvernoteOAuthToken.EDAM_WEB_API_URL_PREFIX))
				.build();
	}

	public EvernoteService getEvernoteService() {
		return evernoteService;
	}
}
