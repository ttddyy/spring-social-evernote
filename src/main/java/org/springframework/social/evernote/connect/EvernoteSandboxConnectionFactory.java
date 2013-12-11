package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteSandboxConnectionFactory extends EvernoteConnectionFactory {

	public EvernoteSandboxConnectionFactory(String consumerKey, String consumerSecret) {
		this(consumerKey, consumerSecret, "evernote-sandbox");
	}

	public EvernoteSandboxConnectionFactory(String consumerKey, String consumerSecret, String providerId) {
		super(consumerKey, consumerSecret, EvernoteService.SANDBOX, providerId);
	}

//    private EvernoteSandboxConnectionFactory(String consumerKey, String consumerSecret, EvernoteService evernoteService) {
//        super(consumerKey, consumerSecret, evernoteService);
//    }
//
//    private EvernoteSandboxConnectionFactory(String consumerKey, String consumerSecret, EvernoteService evernoteService, String providerId) {
//        super(consumerKey, consumerSecret, evernoteService, providerId);
//    }
}
