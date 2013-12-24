package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;
import org.springframework.social.evernote.api.Evernote;
import org.springframework.social.evernote.api.impl.EvernoteTemplate;
import org.springframework.social.oauth1.AbstractOAuth1ServiceProvider;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteServiceProvider extends AbstractOAuth1ServiceProvider<Evernote> {

	private EvernoteService evernoteService;

	public EvernoteServiceProvider(String consumerKey, String consumerSecret) {
		this(consumerKey, consumerSecret, EvernoteService.SANDBOX);  // default is sandbox
	}

	public EvernoteServiceProvider(String consumerKey, String consumerSecret, EvernoteService evernoteService) {
		super(consumerKey, consumerSecret, new EvernoteOAuth1Operations(consumerKey, consumerSecret, evernoteService));
		this.evernoteService = evernoteService;
	}

	@Override
	public Evernote getApi(String accessToken, String secret) {
		return new EvernoteTemplate(this.evernoteService, accessToken);
	}

	// create Evernote from EvernoteOAuthToken which contains more evernote specific info
	public Evernote getApi(EvernoteOAuthToken accessToken) {
		return new EvernoteTemplate(this.evernoteService, accessToken);
	}


	public void setEvernoteService(EvernoteService evernoteService) {
		((EvernoteOAuth1Operations) getOAuthOperations()).setEvernoteService(evernoteService);
	}

}
