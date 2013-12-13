package org.springframework.social.evernote.api.Impl;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.*;
import com.evernote.edam.type.LinkedNotebook;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.social.evernote.api.*;
import org.springframework.social.evernote.connect.EvernoteOAuthToken;

import static org.springframework.social.evernote.api.EvernoteExceptionUtils.Operation;

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
	public ClientFactory clientFactory() {
		return this.clientFactory;
	}

	@Override
	public BusinessNoteStoreClient businessNoteStoreClient() throws EvernoteException {
		return EvernoteExceptionUtils.wrap(new Operation<BusinessNoteStoreClient>() {
			@Override
			public BusinessNoteStoreClient execute() throws Exception {
				return clientFactory.createBusinessNoteStoreClient();
			}
		});

	}

	@Override
	public LinkedNoteStoreClient linkedNoteStoreClient(final LinkedNotebook linkedNotebook) throws EvernoteException {
		return EvernoteExceptionUtils.wrap(new Operation<LinkedNoteStoreClient>() {
			@Override
			public LinkedNoteStoreClient execute() throws Exception {
				return clientFactory.createLinkedNoteStoreClient(linkedNotebook);
			}
		});
	}

	@Override
	public NoteStoreClient noteStoreClient() throws EvernoteException {
		return EvernoteExceptionUtils.wrap(new Operation<NoteStoreClient>() {
			@Override
			public NoteStoreClient execute() throws Exception {
				return clientFactory.createNoteStoreClient();
			}
		});
	}

	@Override
	public UserStoreClient userStoreClient() throws EvernoteException {
		return EvernoteExceptionUtils.wrap(new Operation<UserStoreClient>() {
			@Override
			public UserStoreClient execute() throws Exception {
				return clientFactory.createUserStoreClient();
			}
		});
	}


	@Override
	public BusinessNoteStoreOperations businessNoteStoreClientOperations() {
		BusinessNoteStoreClient businessNoteStoreClient = businessNoteStoreClient();
		return createStoreClientProxy(businessNoteStoreClient, BusinessNoteStoreOperations.class);
	}

	@Override
	public LinkedNoteStoreOperations linkedNoteStoreClientOperations(LinkedNotebook linkedNotebook) {
		LinkedNoteStoreClient linkedNoteStoreClient = linkedNoteStoreClient(linkedNotebook);
		return createStoreClientProxy(linkedNoteStoreClient, LinkedNoteStoreOperations.class);
	}

	@Override
	public NoteStoreOperations noteStoreClientOperations() {
		NoteStoreClient noteStoreClient = noteStoreClient();
		return createStoreClientProxy(noteStoreClient, NoteStoreOperations.class);
	}

	@Override
	public UserStoreOperations userStoreClientOperations() throws EvernoteException {
		UserStoreClient userStoreClient = userStoreClient();
		return createStoreClientProxy(userStoreClient, UserStoreOperations.class);
	}

	private <T> T createStoreClientProxy(Object storeClient, Class<T> operationClass) {
		ProxyFactory proxyFactory = new ProxyFactory(storeClient);
		proxyFactory.addInterface(operationClass);
		proxyFactory.addInterface(StoreClientHolder.class);
		proxyFactory.addAdvice(new ClientStoreMethodInterceptor());
		return (T) proxyFactory.getProxy();
	}

	@Override
	public boolean isAuthorized() {
		return true;
	}
}
