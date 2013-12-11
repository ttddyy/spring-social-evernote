package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.evernote.api.Evernote;
import org.springframework.social.oauth1.OAuth1ServiceProvider;
import org.springframework.social.oauth1.OAuthToken;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteConnectionFactory extends OAuth1ConnectionFactory<Evernote> {

	private EvernoteService evernoteService;

	public EvernoteConnectionFactory(String consumerKey, String consumerSecret) {
		// default is sandbox
		this(consumerKey, consumerSecret, EvernoteService.SANDBOX, "evernote");
	}

	public EvernoteConnectionFactory(String consumerKey, String consumerSecret, EvernoteService evernoteService) {
		this(consumerKey, consumerSecret, evernoteService, "evernote");
	}

	public EvernoteConnectionFactory(String consumerKey, String consumerSecret, EvernoteService evernoteService, String providerId) {
		super(providerId, new EvernoteServiceProvider(consumerKey, consumerSecret, evernoteService), new EvernoteAdapter());
		this.evernoteService = evernoteService;
	}

	public EvernoteService getEvernoteService() {
		return evernoteService;
	}

	@Override
	public Connection<Evernote> createConnection(OAuthToken accessToken) {
		// TODO: use EvernoteOAuthToken, may need to change OAuth1Connection
		return super.createConnection(accessToken);
	}

	@Override
	public Connection<Evernote> createConnection(ConnectionData data) {
		// TODO: use Evernote token data. need to think how to store them.
		return super.createConnection(data);
	}

	@Override
	protected String extractProviderUserId(OAuthToken accessToken) {
		// may not be needed since EvernoteAdapter#setConnectionValues may set it...
		return ((EvernoteOAuthToken) accessToken).getEdamUserId();
	}
}
