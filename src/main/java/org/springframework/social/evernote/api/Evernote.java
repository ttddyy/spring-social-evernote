package org.springframework.social.evernote.api;

import com.evernote.clients.BusinessNoteStoreClient;
import com.evernote.clients.LinkedNoteStoreClient;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import org.springframework.social.ApiBinding;

/**
 * @author Tadaya Tsuyukubo
 */
public interface Evernote extends ApiBinding {

	BusinessNoteStoreClientOperations businessNoteStoreClientOperations();

	LinkedNoteStoreClientOperations linkedNoteStoreClientOperations();

	NoteStoreClientOperations noteStoreClientOperations();

	UserStoreClientOperations userStoreClientOperations();

	// just temp...

	BusinessNoteStoreClient businessNoteStoreClient();

	LinkedNoteStoreClient linkedNoteStoreClient();

	NoteStoreClient noteStoreClient();

	UserStoreClient userStoreClient();

}
