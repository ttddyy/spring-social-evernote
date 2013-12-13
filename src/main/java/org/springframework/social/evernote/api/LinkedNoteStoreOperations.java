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
	 * Helper method to create a note in a linked notebook
	 *
	 * @param note
	 * @param linkedNotebook
	 * @return
	 * @throws com.evernote.edam.error.EDAMUserException
	 *
	 * @throws com.evernote.edam.error.EDAMSystemException
	 *
	 * @throws com.evernote.thrift.TException
	 * @throws com.evernote.edam.error.EDAMNotFoundException
	 *
	 */
	Note createNote(Note note, LinkedNotebook linkedNotebook)
			throws EvernoteException;

	/**
	 * Helper method to list linked notebooks
	 *
	 * @see {@link com.evernote.edam.notestore.NoteStore.Client#listLinkedNotebooks(String)}
	 */
	List<LinkedNotebook> listNotebooks() throws EvernoteException;

	/**
	 * Will return the {@link com.evernote.edam.type.Notebook} associated with the
	 * {@link com.evernote.edam.type.LinkedNotebook} from the linked account
	 *
	 * @param linkedNotebook
	 */
	Notebook getCorrespondingNotebook(LinkedNotebook linkedNotebook) throws EvernoteException;

	/**
	 * Checks writable permissions of {@link LinkedNotebook} on Linked account
	 *
	 * @param linkedNotebook
	 */
	boolean isNotebookWritable(LinkedNotebook linkedNotebook) throws EvernoteException;

}
