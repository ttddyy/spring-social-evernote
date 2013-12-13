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
public interface UserStoreOperations {

	/**
	 * Determine if a user belongs to a business account
	 *
	 * @return the result of a user belonging to a business account
	 */
	boolean isBusinessUser() throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#checkVersion(String, short, short)
	 */
	boolean checkVersion(String clientName, short edamVersionMajor, short edamVersionMinor) throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#getBootstrapInfo(String)
	 */
	BootstrapInfo getBootstrapInfo(String locale) throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#authenticate(String, String, String, String, boolean)
	 */
	AuthenticationResult authenticate(String username, String password, String consumerKey, String consumerSecret,
	                                  boolean supportsTwoFactor) throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#authenticateLongSession(String, String, String,
	 *      String, String, String, boolean)
	 */
	AuthenticationResult authenticateLongSession(String username,
	                                             String password, String consumerKey,
	                                             String consumerSecret, String deviceIdentifier,
	                                             String deviceDescription, boolean supportsTwoFactor)
			throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#authenticateToBusiness(String)
	 */
	AuthenticationResult authenticateToBusiness() throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#refreshAuthentication(String)
	 */
	AuthenticationResult refreshAuthentication() throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#getUser(String)
	 */
	User getUser() throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#getPublicUserInfo(String)
	 */
	PublicUserInfo getPublicUserInfo(String username) throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#getPremiumInfo(String)
	 */
	PremiumInfo getPremiumInfo() throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#getNoteStoreUrl(String)
	 */
	String getNoteStoreUrl() throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#revokeLongSession(String)
	 */
	void revokeLongSession() throws EvernoteException;

	/**
	 * @see com.evernote.edam.userstore.UserStore.Client#completeTwoFactorAuthentication(String, String,
	 *      String, String)
	 */
	void completeTwoFactorAuthentication(String authenticationToken, String oneTimeCode, String deviceIdentifier,
	                                     String deviceDescription) throws EvernoteException;

}
