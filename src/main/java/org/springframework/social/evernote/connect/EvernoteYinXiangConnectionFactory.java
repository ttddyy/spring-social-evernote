package org.springframework.social.evernote.connect;

import com.evernote.auth.EvernoteService;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteYinXiangConnectionFactory extends EvernoteConnectionFactory {

	public EvernoteYinXiangConnectionFactory(String consumerKey, String consumerSecret) {
		this(consumerKey, consumerSecret, EvernoteService.YINXIANG);
	}

	public EvernoteYinXiangConnectionFactory(String consumerKey, String consumerSecret, EvernoteService evernoteService) {
		super(consumerKey, consumerSecret, evernoteService, "evernote-yinxiang");
	}

}
