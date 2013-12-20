package org.springframework.social.evernote.api;

import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.type.LinkedNotebook;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;

import java.util.List;

/**
 * Interface specifying a set of {@link com.evernote.clients.LinkedNoteStoreClient} operations.
 *
 * @author Tadaya Tsuyukubo
 */
public interface LinkedNoteStoreOperations {

	NoteStoreClient getClient();

	/**
	 * Equivalent to {@link com.evernote.clients.LinkedNoteStoreClient#createNote(com.evernote.edam.type.Note, com.evernote.edam.type.LinkedNotebook)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.LinkedNoteStoreClient#createNote(com.evernote.edam.type.Note, com.evernote.edam.type.LinkedNotebook)
	 */
	Note createNote(Note note, LinkedNotebook linkedNotebook) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.LinkedNoteStoreClient#listNotebooks()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.LinkedNoteStoreClient#listNotebooks()
	 */
	List<LinkedNotebook> listNotebooks() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.LinkedNoteStoreClient#getCorrespondingNotebook(com.evernote.edam.type.LinkedNotebook)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.LinkedNoteStoreClient#getCorrespondingNotebook(com.evernote.edam.type.LinkedNotebook)
	 */
	Notebook getCorrespondingNotebook(LinkedNotebook linkedNotebook) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.LinkedNoteStoreClient#isNotebookWritable(com.evernote.edam.type.LinkedNotebook)} ()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.LinkedNoteStoreClient#isNotebookWritable(com.evernote.edam.type.LinkedNotebook)
	 */
	boolean isNotebookWritable(LinkedNotebook linkedNotebook) throws EvernoteException;

}
