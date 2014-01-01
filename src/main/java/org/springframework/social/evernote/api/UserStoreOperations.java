package org.springframework.social.evernote.api;

import com.evernote.edam.type.PremiumInfo;
import com.evernote.edam.type.User;
import com.evernote.edam.userstore.AuthenticationResult;
import com.evernote.edam.userstore.BootstrapInfo;
import com.evernote.edam.userstore.PublicUserInfo;

/**
 * Interface specifying a basic of {@link com.evernote.clients.UserStoreClient} operations.
 *
 * @author Tadaya Tsuyukubo
 */
public interface UserStoreOperations extends StoreOperations {

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#isBusinessUser()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#isBusinessUser()
	 */
	boolean isBusinessUser() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#checkVersion(String, short, short)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#checkVersion(String, short, short)
	 * @see com.evernote.edam.userstore.UserStore.Client#checkVersion(String, short, short)
	 */
	boolean checkVersion(String clientName, short edamVersionMajor, short edamVersionMinor) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#getBootstrapInfo(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#getBootstrapInfo(String)
	 * @see com.evernote.edam.userstore.UserStore.Client#getBootstrapInfo(String)
	 */
	BootstrapInfo getBootstrapInfo(String locale) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#authenticate(String, String, String, String, boolean)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#authenticate(String, String, String, String, boolean)
	 * @see com.evernote.edam.userstore.UserStore.Client#authenticate(String, String, String, String, boolean)
	 */
	AuthenticationResult authenticate(String username, String password, String consumerKey, String consumerSecret,
	                                  boolean supportsTwoFactor) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#authenticateLongSession(String, String, String, String, String, String, boolean)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#authenticateLongSession(String, String, String, String, String, String, boolean)
	 * @see com.evernote.edam.userstore.UserStore.Client#authenticateLongSession(String, String, String,
	 *      String, String, String, boolean)
	 */
	AuthenticationResult authenticateLongSession(String username,
	                                             String password, String consumerKey,
	                                             String consumerSecret, String deviceIdentifier,
	                                             String deviceDescription, boolean supportsTwoFactor)
			throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#authenticateToBusiness()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#authenticateToBusiness()
	 * @see com.evernote.edam.userstore.UserStore.Client#authenticateToBusiness(String)
	 */
	AuthenticationResult authenticateToBusiness() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#refreshAuthentication()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#refreshAuthentication()
	 * @see com.evernote.edam.userstore.UserStore.Client#refreshAuthentication(String)
	 */
	AuthenticationResult refreshAuthentication() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#getUser()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#getUser()
	 * @see com.evernote.edam.userstore.UserStore.Client#getUser(String)
	 */
	User getUser() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#getPublicUserInfo(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#getPublicUserInfo(String)
	 * @see com.evernote.edam.userstore.UserStore.Client#getPublicUserInfo(String)
	 */
	PublicUserInfo getPublicUserInfo(String username) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#getPremiumInfo()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#getPremiumInfo()
	 * @see com.evernote.edam.userstore.UserStore.Client#getPremiumInfo(String)
	 */
	PremiumInfo getPremiumInfo() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#getNoteStoreUrl()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#getNoteStoreUrl()
	 * @see com.evernote.edam.userstore.UserStore.Client#getNoteStoreUrl(String)
	 */
	String getNoteStoreUrl() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#revokeLongSession()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#revokeLongSession()
	 * @see com.evernote.edam.userstore.UserStore.Client#revokeLongSession(String)
	 */
	void revokeLongSession() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.UserStoreClient#completeTwoFactorAuthentication(String, String, String, String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.UserStoreClient#completeTwoFactorAuthentication(String, String, String, String)
	 * @see com.evernote.edam.userstore.UserStore.Client#completeTwoFactorAuthentication(String, String, String, String)
	 */
	void completeTwoFactorAuthentication(String authenticationToken, String oneTimeCode, String deviceIdentifier,
	                                     String deviceDescription) throws EvernoteException;

}
