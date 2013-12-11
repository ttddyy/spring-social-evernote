package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteProductionConnectionFactory extends EvernoteConnectionFactory {

	public EvernoteProductionConnectionFactory(String consumerKey, String consumerSecret) {
		this(consumerKey, consumerSecret, EvernoteService.PRODUCTION);
	}

	public EvernoteProductionConnectionFactory(String consumerKey, String consumerSecret, EvernoteService evernoteService) {
		super(consumerKey, consumerSecret, evernoteService, "evernote-production");
	}

}
