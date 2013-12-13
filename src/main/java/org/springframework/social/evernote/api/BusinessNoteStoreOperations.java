package org.springframework.social.evernote.api;

import com.evernote.edam.type.LinkedNotebook;
import com.evernote.edam.type.Notebook;

import java.util.List;

/**
 * Interface specifying a set of {@link com.evernote.clients.BusinessNoteStoreClient} operations.
 *
 * @author Tadaya Tsuyukubo
 */
public interface BusinessNoteStoreOperations extends LinkedNoteStoreOperations {

	LinkedNotebook createNotebook(Notebook notebook) throws EvernoteException;

	/**
	 * Helper method to list business notebooks synchronously
	 */
	List<LinkedNotebook> listNotebooks() throws EvernoteException;


}
