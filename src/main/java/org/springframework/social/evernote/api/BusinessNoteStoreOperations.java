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

	/**
	 * Equivalent to {@link com.evernote.clients.BusinessNoteStoreClient#createNotebook(com.evernote.edam.type.Notebook)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.BusinessNoteStoreClient#createNotebook(com.evernote.edam.type.Notebook)
	 */
	LinkedNotebook createNotebook(Notebook notebook) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.BusinessNoteStoreClient#listNotebooks()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.BusinessNoteStoreClient#listNotebooks()
	 */
	List<LinkedNotebook> listNotebooks() throws EvernoteException;


}
