package org.springframework.social.evernote.api;

import com.evernote.clients.*;
import com.evernote.edam.type.LinkedNotebook;
import org.springframework.social.ApiBinding;

/**
 * @author Tadaya Tsuyukubo
 */
public interface Evernote extends ApiBinding {

	BusinessNoteStoreOperations businessNoteStoreClientOperations();

	LinkedNoteStoreOperations linkedNoteStoreClientOperations(LinkedNotebook linkedNotebook);

	NoteStoreOperations noteStoreClientOperations();

	UserStoreOperations userStoreClientOperations();


	// to retrieve actual store client instances

	ClientFactory clientFactory();

	BusinessNoteStoreClient businessNoteStoreClient();

	LinkedNoteStoreClient linkedNoteStoreClient(LinkedNotebook linkedNotebook);

	NoteStoreClient noteStoreClient();

	UserStoreClient userStoreClient();

}
