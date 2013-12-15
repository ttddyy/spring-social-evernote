package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.social.oauth1.*;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * This class works as a delegator to the selected {@link OAuth1Operations} by {@link EvernoteService}.
 * <p/>
 * Evernote has three endpoints - sandbox, production, and yinxiang.
 * Since {@link OAuth1Template} is immutable, instanciate template for all environment and choose one of them
 * based on the passed {@link EvernoteService}.
 * All method delegates to the selected {@link OAuth1Operations}.
 *
 * @author Tadaya Tsuyukubo
 */
public class EvernoteOAuth1Operations implements OAuth1Operations {

	private Map<EvernoteService, OAuth1Template> map = new HashMap<EvernoteService, OAuth1Template>();
	private OAuth1Operations selectedOauth1Operations;
	private String consumerKey;

	public EvernoteOAuth1Operations(String consumerKey, String consumerSecret) {
		this(consumerKey, consumerSecret, EvernoteService.SANDBOX);  // default sandbox
	}

	public EvernoteOAuth1Operations(String consumerKey, String consumerSecret, EvernoteService evernoteService) {
		// since OAuth1Template has immutable urls, create one for each EvernoteService environment.
		for (EvernoteService service : EvernoteService.values()) {
			map.put(service, new EvernoteOAuth1Template(consumerKey, consumerSecret, service));
		}
		this.consumerKey = consumerKey;
		selectedOauth1Operations = map.get(evernoteService);
	}

	public void setEvernoteService(EvernoteService evernoteService) {
		selectedOauth1Operations = map.get(evernoteService);
	}

	@Override
	public OAuth1Version getVersion() {
		return selectedOauth1Operations.getVersion();  // delegate
	}

	@Override
	public OAuthToken fetchRequestToken(String callbackUrl, MultiValueMap<String, String> additionalParameters) {
		return selectedOauth1Operations.fetchRequestToken(callbackUrl, additionalParameters);  // delegate
	}

	@Override
	public String buildAuthorizeUrl(String requestToken, OAuth1Parameters parameters) {
		return selectedOauth1Operations.buildAuthorizeUrl(requestToken, parameters);  // delegate
	}

	@Override
	public String buildAuthenticateUrl(String requestToken, OAuth1Parameters parameters) {
		return selectedOauth1Operations.buildAuthorizeUrl(requestToken, parameters);  // delegate
	}

	@Override
	public OAuthToken exchangeForAccessToken(AuthorizedRequestToken requestToken, MultiValueMap<String, String> additionalParameters) {
		return selectedOauth1Operations.exchangeForAccessToken(requestToken, additionalParameters);  // delegate
	}

	public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
		for (OAuth1Template template : map.values()) {
			template.setRequestFactory(requestFactory);
		}
	}

	public OAuth1Operations getSelectedOauth1Operations() {
		return selectedOauth1Operations;
	}

	// TODO: need this??
	protected String getConsumerKey() {
		return this.consumerKey;
	}

}
