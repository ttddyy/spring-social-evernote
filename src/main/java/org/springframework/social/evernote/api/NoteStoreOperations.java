package org.springframework.social.evernote.api;

import com.evernote.edam.notestore.*;
import com.evernote.edam.type.*;
import com.evernote.edam.userstore.AuthenticationResult;

import java.util.List;

/**
 * Interface specifying a basic of {@link com.evernote.clients.NoteStoreClient} operations.
 *
 * @author Tadaya Tsuyukubo
 */
public interface NoteStoreOperations {

	/**
	 * If direct access to the Note Store is needed, all of these calls are
	 * synchronous
	 *
	 * @return {@link com.evernote.edam.notestore.NoteStore.Client}
	 */
	NoteStore.Client getClient();

	/**
	 * @see NoteStore.Client#getSyncState(String)
	 */
	SyncState getSyncState() throws EvernoteException;

	/**
	 * @see NoteStore.Client##getSyncStateWithMetrics(com.evernote.edam.notestore
	 *      .ClientUsageMetrics, OnClientCallback)
	 */
	SyncState getSyncStateWithMetrics(ClientUsageMetrics clientMetrics)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#getSyncChunk(String, int, int, boolean)
	 */
	SyncChunk getSyncChunk(int afterUSN, int maxEntries,
	                       boolean fullSyncOnly) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getFilteredSyncChunk(String, int, int,
	 *      com.evernote.edam.notestore.SyncChunkFilter)
	 */
	SyncChunk getFilteredSyncChunk(int afterUSN, int maxEntries,
	                               SyncChunkFilter filter) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getLinkedNotebookSyncState(String,
	 *      com.evernote.edam.type.LinkedNotebook)
	 */
	SyncState getLinkedNotebookSyncState(LinkedNotebook linkedNotebook)
			throws EvernoteException;

	/**
	 * @return
	 * @see NoteStore.Client#getLinkedNotebookSyncChunk(String,
	 *      com.evernote.edam.type.LinkedNotebook, int, int, boolean)
	 */
	SyncChunk getLinkedNotebookSyncChunk(LinkedNotebook linkedNotebook,
	                                     int afterUSN, int maxEntries, boolean fullSyncOnly)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#listNotebooks(String)
	 */
	List<Notebook> listNotebooks() throws EvernoteException;

	/**
	 * @see NoteStore.Client#getNotebook(String, String)
	 */
	Notebook getNotebook(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getDefaultNotebook(String)
	 */
	Notebook getDefaultNotebook() throws EvernoteException;

	/**
	 * @see NoteStore.Client#createNotebook(String,
	 *      com.evernote.edam.type.Notebook)
	 */
	Notebook createNotebook(Notebook notebook) throws EvernoteException;

	/**
	 * @see NoteStore.Client#updateNotebook(String,
	 *      com.evernote.edam.type.Notebook)
	 */
	int updateNotebook(Notebook notebook) throws EvernoteException;

	/**
	 * @see NoteStore.Client#expungeNotebook(String, String)
	 */
	int expungeNotebook(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#listTags(String)
	 */
	List<Tag> listTags() throws EvernoteException;

	/**
	 * @see NoteStore.Client#listTagsByNotebook(String, String)
	 */
	List<Tag> listTagsByNotebook(String notebookGuid)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#getTag(String, String)
	 */
	Tag getTag(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#createTag(String, com.evernote.edam.type.Tag)
	 */
	Tag createTag(Tag tag) throws EvernoteException;

	/**
	 * @see NoteStore.Client#updateTag(String, com.evernote.edam.type.Tag)
	 */
	int updateTag(Tag tag) throws EvernoteException;

	/**
	 * @see NoteStore.Client#untagAll(String, String)
	 */
	void untagAll(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#expungeTag(String, String)
	 */
	int expungeTag(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#listSearches(String)
	 */
	List<SavedSearch> listSearches() throws EvernoteException;

	/**
	 * @see NoteStore.Client#getSearch(String, String)
	 */
	SavedSearch getSearch(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#createSearch(String,
	 *      com.evernote.edam.type.SavedSearch)
	 */
	SavedSearch createSearch(SavedSearch search) throws EvernoteException;

	/**
	 * @see NoteStore.Client#updateSearch(String,
	 *      com.evernote.edam.type.SavedSearch)
	 */
	int updateSearch(SavedSearch search) throws EvernoteException;

	/**
	 * @see NoteStore.Client#expungeSearch(String, String)
	 */
	int expungeSearch(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#findNotes(String,
	 *      com.evernote.edam.notestore.NoteFilter, int, int)
	 */
	NoteList findNotes(NoteFilter filter, int offset, int maxNotes)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#findNoteOffset(String,
	 *      com.evernote.edam.notestore.NoteFilter, String)
	 */
	int findNoteOffset(NoteFilter filter, String guid)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#findNotesMetadata(String,
	 *      com.evernote.edam.notestore.NoteFilter, int, int,
	 *      com.evernote.edam.notestore.NotesMetadataResultSpec)
	 */
	NotesMetadataList findNotesMetadata(NoteFilter filter, int offset,
	                                    int maxNotes, NotesMetadataResultSpec resultSpec)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#findNoteCounts(String,
	 *      com.evernote.edam.notestore.NoteFilter, boolean)
	 */
	NoteCollectionCounts findNoteCounts(NoteFilter filter,
	                                    boolean withTrash) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getNote(String, String, boolean, boolean, boolean,
	 *      boolean)
	 */
	Note getNote(String guid, boolean withContent,
	             boolean withResourcesData, boolean withResourcesRecognition,
	             boolean withResourcesAlternateData) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getNoteApplicationData(String, String)
	 */
	LazyMap getNoteApplicationData(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getNoteApplicationDataEntry(String, String, String)
	 */
	String getNoteApplicationDataEntry(String guid, String key)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#setNoteApplicationDataEntry(String, String, String,
	 *      String)
	 */
	int setNoteApplicationDataEntry(String guid, String key, String value)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#unsetNoteApplicationDataEntry(String, String, String)
	 */
	int unsetNoteApplicationDataEntry(String guid, String key)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#getNoteContent(String, String)
	 */
	String getNoteContent(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getNoteSearchText(String, String, boolean, boolean)
	 */
	String getNoteSearchText(String guid, boolean noteOnly,
	                         boolean tokenizeForIndexing) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getResourceSearchText(String, String)
	 */
	String getResourceSearchText(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getNoteTagNames(String, String)
	 */
	List<String> getNoteTagNames(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#createNote(String, com.evernote.edam.type.Note)
	 */
	Note createNote(Note note) throws EvernoteException;

	/**
	 * @see NoteStore.Client#updateNote(String, com.evernote.edam.type.Note)
	 */
	Note updateNote(Note note) throws EvernoteException;

	/**
	 * @see NoteStore.Client#deleteNote(String, String)
	 */
	int deleteNote(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#expungeNote(String, String)
	 */
	int expungeNote(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#expungeNotes(String, java.util.List)
	 */
	int expungeNotes(List<String> noteGuids) throws EvernoteException;

	/**
	 * @see NoteStore.Client#expungeInactiveNotes(String)
	 */
	int expungeInactiveNotes() throws EvernoteException;

	/**
	 * @see NoteStore.Client#copyNote(String, String, String)
	 */
	Note copyNote(String noteGuid, String toNotebookGuid)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#listNoteVersions(String, String)
	 */
	List<NoteVersionId> listNoteVersions(String noteGuid)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#getNoteVersion(String, String, int, boolean, boolean,
	 *      boolean)
	 */
	Note getNoteVersion(String noteGuid, int updateSequenceNum,
	                    boolean withResourcesData, boolean withResourcesRecognition,
	                    boolean withResourcesAlternateData) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getResource(String, String, boolean, boolean,
	 *      boolean, boolean)
	 */
	Resource getResource(String guid, boolean withData,
	                     boolean withRecognition, boolean withAttributes, boolean withAlternateData)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#getResourceApplicationData(String, String)
	 */
	LazyMap getResourceApplicationData(String guid)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#getResourceApplicationDataEntry(String, String,
	 *      String)
	 */
	String getResourceApplicationDataEntry(String guid, String key)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#setResourceApplicationDataEntry(String, String,
	 *      String, String)
	 */
	int setResourceApplicationDataEntry(String guid, String key,
	                                    String value) throws EvernoteException;

	/**
	 * @see NoteStore.Client#unsetResourceApplicationDataEntry(String, String,
	 *      String)
	 */
	int unsetResourceApplicationDataEntry(String guid, String key)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#updateResource(String,
	 *      com.evernote.edam.type.Resource)
	 */
	int updateResource(Resource resource) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getResourceData(String, String)
	 */
	byte[] getResourceData(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getResourceByHash(String, String, byte[], boolean,
	 *      boolean, boolean)
	 */
	Resource getResourceByHash(String noteGuid, byte[] contentHash,
	                           boolean withData, boolean withRecognition, boolean withAlternateData)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#getResourceRecognition(String, String)
	 */
	byte[] getResourceRecognition(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getResourceAlternateData(String, String)
	 */
	byte[] getResourceAlternateData(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getResourceAttributes(String, String)
	 */
	ResourceAttributes getResourceAttributes(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getPublicNotebook(int, String)
	 */
	Notebook getPublicNotebook(int userId, String publicUri) throws EvernoteException;

	/**
	 * @see NoteStore.Client#createSharedNotebook(String,
	 *      com.evernote.edam.type.SharedNotebook)
	 */
	SharedNotebook createSharedNotebook(SharedNotebook sharedNotebook) throws EvernoteException;

	/**
	 * @see NoteStore.Client#updateSharedNotebook(String,
	 *      com.evernote.edam.type.SharedNotebook)
	 */
	int updateSharedNotebook(SharedNotebook sharedNotebook) throws EvernoteException;

	/**
	 * @see NoteStore.Client#sendMessageToSharedNotebookMembers(String, String,
	 *      String, java.util.List)
	 */
	int sendMessageToSharedNotebookMembers(String notebookGuid, String messageText, List<String> recipients)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#listSharedNotebooks(String)
	 */
	List<SharedNotebook> listSharedNotebooks() throws EvernoteException;

	/**
	 * @see NoteStore.Client#expungeSharedNotebooks(String, java.util.List)
	 */
	int expungeSharedNotebooks(List<Long> sharedNotebookIds) throws EvernoteException;

	/**
	 * @see NoteStore.Client#createLinkedNotebook(String,
	 *      com.evernote.edam.type.LinkedNotebook)
	 */
	LinkedNotebook createLinkedNotebook(LinkedNotebook linkedNotebook) throws EvernoteException;

	/**
	 * @see NoteStore.Client#updateLinkedNotebook(String,
	 *      com.evernote.edam.type.LinkedNotebook)
	 */
	int updateLinkedNotebook(LinkedNotebook linkedNotebook) throws EvernoteException;

	/**
	 * @see NoteStore.Client#listLinkedNotebooks(String)
	 */
	List<LinkedNotebook> listLinkedNotebooks() throws EvernoteException;

	/**
	 * @see NoteStore.Client#expungeLinkedNotebook(String, String)
	 */
	int expungeLinkedNotebook(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#authenticateToSharedNotebook(String, String)
	 */
	AuthenticationResult authenticateToSharedNotebook(String shareKey) throws EvernoteException;

	/**
	 * @see NoteStore.Client#getSharedNotebookByAuth(String)
	 */
	SharedNotebook getSharedNotebookByAuth() throws EvernoteException;

	/**
	 * @see NoteStore.Client#emailNote(String,
	 *      com.evernote.edam.notestore.NoteEmailParameters)
	 */
	void emailNote(NoteEmailParameters parameters) throws EvernoteException;

	/**
	 * @see NoteStore.Client#shareNote(String, String)
	 */
	String shareNote(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#stopSharingNote(String, String)
	 */
	void stopSharingNote(String guid) throws EvernoteException;

	/**
	 * @see NoteStore.Client#authenticateToSharedNote(String, String)
	 */
	AuthenticationResult authenticateToSharedNote(String guid, String noteKey, String authenticationToken)
			throws EvernoteException;

	/**
	 * @see NoteStore.Client#findRelated(String,
	 *      com.evernote.edam.notestore.RelatedQuery,
	 *      com.evernote.edam.notestore.RelatedResultSpec)
	 */
	RelatedResult findRelated(RelatedQuery query, RelatedResultSpec resultSpec) throws EvernoteException;

	/**
	 * @see NoteStore.Client#setSharedNotebookRecipientSettings(String, long,
	 *      SharedNotebookRecipientSettings)
	 */
	void setSharedNotebookRecipientSettings(String authenticationToken, long sharedNotebookId,
	                                        SharedNotebookRecipientSettings recipientSettings)
			throws EvernoteException;

}
