package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.social.oauth1.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.social.evernote.connect.EvernoteOAuthToken.EvernoteOAuthTokenBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteOAuth1Operations implements OAuth1Operations {

	private Map<EvernoteService, OAuth1Template> map = new HashMap<EvernoteService, OAuth1Template>();
	private OAuth1Operations selectedOauth1Operation;
	private String consumerKey;

	public EvernoteOAuth1Operations(String consumerKey, String consumerSecret) {
		this(consumerKey, consumerSecret, EvernoteService.SANDBOX);  // default sandbox
	}

	public EvernoteOAuth1Operations(String consumerKey, String consumerSecret, EvernoteService evernoteService) {
		// since OAuth1Template has immutable urls, create one for each EvernoteService environment.
		// TODO: move to instance initializer??
		for (EvernoteService service : EvernoteService.values()) {
			final OAuth1Template template = new OAuth1Template(consumerKey, consumerSecret,
					evernoteService.getRequestTokenEndpoint(),  // https://evernoteHost/oauth
					getAuthorizationUrl(evernoteService),  // https://evernoteHost/OAuth.action
					evernoteService.getAccessTokenEndpoint()  // https://evernoteHost/oauth
			) {

				// TODO: create EvernoteOauthTemplate...
				@Override
				protected OAuthToken createOAuthToken(String tokenValue, String tokenSecret, MultiValueMap<String, String> response) {
					return new EvernoteOAuthTokenBuilder()
							.setToken(response.getFirst(EvernoteOAuthToken.KEY_OAUTH_TOKEN))
							.setSecret(response.getFirst(EvernoteOAuthToken.KEY_OAUTH_TOKEN_SECRET))
							.setEdamShard(response.getFirst(EvernoteOAuthToken.EDAM_SHARD))
							.setEdamUserId(response.getFirst(EvernoteOAuthToken.EDAM_USER_ID))
							.setEdamExpires(response.getFirst(EvernoteOAuthToken.EDAM_EXPIRES))
							.setEdamNoteStoreUrl(response.getFirst(EvernoteOAuthToken.EDAM_NOTE_STORE_URL))
							.setEdamWebApiUrlPrefix(response.getFirst(EvernoteOAuthToken.EDAM_WEB_API_URL_PREFIX))
							.build();
				}
			};
			map.put(service, template);
		}
		this.consumerKey = consumerKey;
		selectedOauth1Operation = map.get(evernoteService);
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


	public void setEvernoteService(EvernoteService evernoteService) {
		selectedOauth1Operation = map.get(evernoteService);
	}

	@Override
	public OAuth1Version getVersion() {
		return selectedOauth1Operation.getVersion();  // delegate
	}

	@Override
	public OAuthToken fetchRequestToken(String callbackUrl, MultiValueMap<String, String> additionalParameters) {
		return selectedOauth1Operation.fetchRequestToken(callbackUrl, additionalParameters);  // delegate
	}

	@Override
	public String buildAuthorizeUrl(String requestToken, OAuth1Parameters parameters) {
		return selectedOauth1Operation.buildAuthorizeUrl(requestToken, parameters);  // delegate
	}

	@Override
	public String buildAuthenticateUrl(String requestToken, OAuth1Parameters parameters) {
		return selectedOauth1Operation.buildAuthorizeUrl(requestToken, parameters);  // delegate
	}

	@Override
	public OAuthToken exchangeForAccessToken(AuthorizedRequestToken requestToken, MultiValueMap<String, String> additionalParameters) {
		return selectedOauth1Operation.exchangeForAccessToken(requestToken, additionalParameters);  // delegate
	}

	public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
		for (OAuth1Template template : map.values()) {
			template.setRequestFactory(requestFactory);
		}
	}

	// TODO: need this??
	protected String getConsumerKey() {
		return this.consumerKey;
	}

}
