package org.springframework.social.evernote.api;

import com.evernote.clients.*;
import com.evernote.edam.type.LinkedNotebook;
import org.springframework.social.ApiBinding;

/**
 * Interface specifying basic set of operations to evernote.
 * <p/>
 * This interface provides spring-social-evernote's core API for features, such as:
 * <ul>
 * <li>Interface based programming model</li>
 * <li>Unchecked exception</li>
 * <li>null-safe thrift collections</li>
 * </ul>
 * <p/>
 * Implemented by {@link org.springframework.social.evernote.api.Impl.EvernoteTemplate}.
 *
 * @author Tadaya Tsuyukubo
 */
public interface Evernote extends ApiBinding {

	/**
	 * API for performing operations on {@link BusinessNoteStoreClient}.
	 *
	 * @return operations for {@link BusinessNoteStoreClient}.
	 * @throws EvernoteException converted unchecked exception.
	 */
	BusinessNoteStoreOperations businessNoteStoreOperations() throws EvernoteException;

	/**
	 * API for performing operations on {@link LinkedNoteStoreClient}.
	 *
	 * @param linkedNotebook
	 * @return operations for {@link LinkedNoteStoreClient}.
	 * @throws EvernoteException converted unchecked exception.
	 */
	LinkedNoteStoreOperations linkedNoteStoreOperations(LinkedNotebook linkedNotebook) throws EvernoteException;

	/**
	 * API for performing operations on {@link NoteStoreClient}.
	 *
	 * @return operations for {@link NoteStoreClient}.
	 * @throws EvernoteException converted unchecked exception.
	 */
	NoteStoreOperations noteStoreOperations() throws EvernoteException;

	/**
	 * API for performing operations on {@link UserStoreClient}.
	 *
	 * @return operations for {@link UserStoreClient}.
	 * @throws EvernoteException converted unchecked exception.
	 */
	UserStoreOperations userStoreOperations() throws EvernoteException;


	/**
	 * Returns underlying {@link ClientFactory} instance.
	 *
	 * @return {@link ClientFactory} instance.
	 */
	ClientFactory clientFactory();

	/**
	 * Retrieve {@link BusinessNoteStoreClient} instance from underlying {@link ClientFactory}.
	 * <p/>
	 * When checked exception has thrown while creating {@link BusinessNoteStoreClient}, it will be converted
	 * to {@link EvernoteException} which is an unchecked exception.
	 *
	 * @return {@link BusinessNoteStoreClient} instance.
	 * @throws EvernoteException encapsulating thrown checked exception.
	 */
	BusinessNoteStoreClient businessNoteStoreClient() throws EvernoteException;

	/**
	 * Retrieve {@link LinkedNoteStoreClient} instance from underlying {@link ClientFactory}.
	 * <p/>
	 * When checked exception has thrown while creating {@link LinkedNoteStoreClient}, it will be converted
	 * to {@link EvernoteException} which is an unchecked exception.
	 *
	 * @return {@link LinkedNoteStoreClient} instance.
	 * @throws EvernoteException encapsulating thrown checked exception.
	 */
	LinkedNoteStoreClient linkedNoteStoreClient(LinkedNotebook linkedNotebook) throws EvernoteException;

	/**
	 * Retrieve {@link NoteStoreClient} instance from underlying {@link ClientFactory}.
	 * <p/>
	 * When checked exception has thrown while creating {@link NoteStoreClient}, it will be converted
	 * to {@link EvernoteException} which is an unchecked exception.
	 *
	 * @return {@link NoteStoreClient} instance.
	 * @throws EvernoteException encapsulating thrown checked exception.
	 */
	NoteStoreClient noteStoreClient() throws EvernoteException;

	/**
	 * Retrieve {@link UserStoreClient} instance from underlying {@link ClientFactory}.
	 * <p/>
	 * When checked exception has thrown while creating {@link UserStoreClient}, it will be converted
	 * to {@link EvernoteException} which is an unchecked exception.
	 *
	 * @return {@link UserStoreClient} instance.
	 * @throws EvernoteException encapsulating thrown checked exception.
	 */
	UserStoreClient userStoreClient() throws EvernoteException;

}
