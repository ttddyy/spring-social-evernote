package org.springframework.social.evernote.api.impl;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.*;
import com.evernote.edam.type.LinkedNotebook;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.social.evernote.api.*;
import org.springframework.social.evernote.connect.EvernoteOAuthToken;

import static org.springframework.social.evernote.api.EvernoteExceptionUtils.Operation;

/**
 * API Binding implementation for {@link Evernote}.
 *
 * @author Tadaya Tsuyukubo
 */
public class EvernoteTemplate implements Evernote {
	private EvernoteService evernoteService;
	private ClientFactory clientFactory;
	private boolean applyNullSafe = true;

	public EvernoteTemplate(EvernoteService evernoteService, String accessToken) {
		this.evernoteService = evernoteService;
		EvernoteAuth evernoteAuth = new EvernoteAuth(evernoteService, accessToken);
		this.clientFactory = new ClientFactory(evernoteAuth);
	}

	public EvernoteTemplate(EvernoteService evernoteService, String token, String noteStoreUrl, String webApiUrlPrefix, String userId) {
		this(evernoteService, token, noteStoreUrl, webApiUrlPrefix, Integer.parseInt(userId));
	}

	public EvernoteTemplate(EvernoteService evernoteService, String token, String noteStoreUrl, String webApiUrlPrefix, int userId) {
		this(evernoteService,
				new EvernoteOAuthToken.EvernoteOAuthTokenBuilder()
						.setToken(token)
						.setEdamNoteStoreUrl(noteStoreUrl)
						.setEdamWebApiUrlPrefix(webApiUrlPrefix)
						.setEdamUserId(String.valueOf(userId))
						.build());
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
	public BusinessNoteStoreOperations businessNoteStoreOperations() {
		BusinessNoteStoreClient businessNoteStoreClient = businessNoteStoreClient();
		return createOperationsProxy(BusinessNoteStoreOperations.class, businessNoteStoreClient);
	}

	@Override
	public LinkedNoteStoreOperations linkedNoteStoreOperations(LinkedNotebook linkedNotebook) {
		LinkedNoteStoreClient linkedNoteStoreClient = linkedNoteStoreClient(linkedNotebook);
		return createOperationsProxy(LinkedNoteStoreOperations.class, linkedNoteStoreClient);
	}

	@Override
	public NoteStoreOperations noteStoreOperations() {
		NoteStoreClient noteStoreClient = noteStoreClient();
		return createOperationsProxy(NoteStoreOperations.class, noteStoreClient);
	}

	@Override
	public UserStoreOperations userStoreOperations() throws EvernoteException {
		UserStoreClient userStoreClient = userStoreClient();
		return createOperationsProxy(UserStoreOperations.class, userStoreClient);
	}

	private <T> T createOperationsProxy(Class<T> operationClass, Object storeClient) {
		ProxyFactory proxyFactory = new ProxyFactory(storeClient);
		proxyFactory.addInterface(operationClass);
		proxyFactory.addInterface(StoreClientHolder.class);
		if (this.applyNullSafe) {
			// when registering, this interceptor has to be before ClientStoreMethodInterceptor
			proxyFactory.addAdvice(new ThriftWrapperInterceptor());
		}
		proxyFactory.addAdvice(new ClientStoreMethodInterceptor());
		return (T) proxyFactory.getProxy();
	}

	@Override
	public boolean isAuthorized() {
		return true;
	}

	@Override
	public void setApplyNullSafe(boolean applyNullSafe) {
		this.applyNullSafe = applyNullSafe;
	}

}
