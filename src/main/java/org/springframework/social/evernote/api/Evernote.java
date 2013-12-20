package org.springframework.social.evernote.api;

import com.evernote.clients.*;
import com.evernote.edam.type.LinkedNotebook;
import org.springframework.social.ApiBinding;

/**
 * @author Tadaya Tsuyukubo
 */
public interface Evernote extends ApiBinding {

	BusinessNoteStoreOperations businessNoteStoreOperations();

	LinkedNoteStoreOperations linkedNoteStoreOperations(LinkedNotebook linkedNotebook);

	NoteStoreOperations noteStoreOperations();

	UserStoreOperations userStoreOperations();


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
