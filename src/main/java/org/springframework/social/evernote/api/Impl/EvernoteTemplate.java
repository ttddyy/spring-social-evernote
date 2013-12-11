package org.springframework.social.evernote.api.Impl;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.*;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;
import org.springframework.social.evernote.api.*;
import org.springframework.social.evernote.connect.EvernoteOAuthToken;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;

/**
 * TODO: impl
 *
 * @author Tadaya Tsuyukubo
 */
public class EvernoteTemplate implements Evernote {
	private EvernoteService evernoteService;
	private ClientFactory clientFactory;

	public EvernoteTemplate(EvernoteService evernoteService, String accessToken) {
		this.evernoteService = evernoteService;
		EvernoteAuth evernoteAuth = new EvernoteAuth(evernoteService, accessToken);
		this.clientFactory = new ClientFactory(evernoteAuth);
	}

	public EvernoteTemplate(EvernoteService evernoteService, EvernoteOAuthToken accessToken) {

		int userId = 0;
		try {
			userId = Integer.parseInt(accessToken.getEdamUserId());
		} catch (NumberFormatException e) {
		}
		EvernoteAuth evernoteAuth = new EvernoteAuth(this.evernoteService, accessToken.getValue(), accessToken.getEdamNoteStoreUrl(), accessToken.getEdamWebApiUrlPrefix(), userId);

		this.evernoteService = evernoteService;
		this.clientFactory = new ClientFactory(evernoteAuth);
	}

	@Override
	public BusinessNoteStoreClientOperations businessNoteStoreClientOperations() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public LinkedNoteStoreClientOperations linkedNoteStoreClientOperations() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public NoteStoreClientOperations noteStoreClientOperations() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public UserStoreClientOperations userStoreClientOperations() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public BusinessNoteStoreClient businessNoteStoreClient() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public LinkedNoteStoreClient linkedNoteStoreClient() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public NoteStoreClient noteStoreClient() {
		// TODO: wrap
		try {
			return this.clientFactory.createNoteStoreClient();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public UserStoreClient userStoreClient() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean isAuthorized() {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
