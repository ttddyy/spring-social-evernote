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
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getSyncState()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getSyncState()
	 * @see NoteStore.Client#getSyncState(String)
	 */
	SyncState getSyncState() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getSyncStateWithMetrics(com.evernote.edam.notestore.ClientUsageMetrics)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getSyncStateWithMetrics(com.evernote.edam.notestore.ClientUsageMetrics)
	 * @see NoteStore.Client#getSyncStateWithMetrics(String, com.evernote.edam.notestore.ClientUsageMetrics)
	 */
	SyncState getSyncStateWithMetrics(ClientUsageMetrics clientMetrics) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getSyncChunk(int, int, boolean)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getSyncChunk(int, int, boolean)
	 * @see NoteStore.Client#getSyncChunk(String, int, int, boolean)
	 */
	SyncChunk getSyncChunk(int afterUSN, int maxEntries, boolean fullSyncOnly) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getFilteredSyncChunk(int, int, com.evernote.edam.notestore.SyncChunkFilter)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getFilteredSyncChunk(int, int, com.evernote.edam.notestore.SyncChunkFilter)
	 * @see NoteStore.Client#getFilteredSyncChunk(String, int, int, com.evernote.edam.notestore.SyncChunkFilter)
	 */
	SyncChunk getFilteredSyncChunk(int afterUSN, int maxEntries, SyncChunkFilter filter) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getLinkedNotebookSyncState(com.evernote.edam.type.LinkedNotebook)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getLinkedNotebookSyncState(com.evernote.edam.type.LinkedNotebook)
	 * @see NoteStore.Client#getLinkedNotebookSyncState(String, com.evernote.edam.type.LinkedNotebook)
	 */
	SyncState getLinkedNotebookSyncState(LinkedNotebook linkedNotebook) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getLinkedNotebookSyncChunk(com.evernote.edam.type.LinkedNotebook, int, int, boolean)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getLinkedNotebookSyncChunk(com.evernote.edam.type.LinkedNotebook, int, int, boolean)
	 * @see NoteStore.Client#getLinkedNotebookSyncChunk(String, com.evernote.edam.type.LinkedNotebook, int, int, boolean)
	 */
	SyncChunk getLinkedNotebookSyncChunk(LinkedNotebook linkedNotebook, int afterUSN, int maxEntries, boolean fullSyncOnly)
			throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#listNotebooks()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#listNotebooks()
	 * @see NoteStore.Client#listNotebooks(String)
	 */
	List<Notebook> listNotebooks() throws EvernoteException;

	/**
	 * Equivalent to {@link NoteStore.Client#getNotebook(String, String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getNotebook(String)
	 * @see NoteStore.Client#getNotebook(String, String)
	 */
	Notebook getNotebook(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getDefaultNotebook()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getDefaultNotebook()
	 * @see NoteStore.Client#getDefaultNotebook(String)
	 */
	Notebook getDefaultNotebook() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#createNotebook(com.evernote.edam.type.Notebook)} .
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#createNotebook(com.evernote.edam.type.Notebook)
	 * @see NoteStore.Client#createNotebook(String, com.evernote.edam.type.Notebook)
	 */
	Notebook createNotebook(Notebook notebook) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#updateNotebook(com.evernote.edam.type.Notebook)} .
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#updateNotebook(com.evernote.edam.type.Notebook)
	 * @see NoteStore.Client#updateNotebook(String, com.evernote.edam.type.Notebook)
	 */
	int updateNotebook(Notebook notebook) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#expungeNotebook(String)} .
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#expungeNotebook(String)
	 * @see NoteStore.Client#expungeNotebook(String, String)
	 */
	int expungeNotebook(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#listTags()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#listTags()
	 * @see NoteStore.Client#listTags(String)
	 */
	List<Tag> listTags() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#listTagsByNotebook(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#listTagsByNotebook(String)
	 * @see NoteStore.Client#listTagsByNotebook(String, String)
	 */
	List<Tag> listTagsByNotebook(String notebookGuid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getTag(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getTag(String)
	 * @see NoteStore.Client#getTag(String, String)
	 */
	Tag getTag(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#createTag(com.evernote.edam.type.Tag)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#createTag(com.evernote.edam.type.Tag)
	 * @see NoteStore.Client#createTag(String, com.evernote.edam.type.Tag)
	 */
	Tag createTag(Tag tag) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#updateTag(com.evernote.edam.type.Tag)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#updateTag(com.evernote.edam.type.Tag)
	 * @see NoteStore.Client#updateTag(String, com.evernote.edam.type.Tag)
	 */
	int updateTag(Tag tag) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#untagAll(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#untagAll(String)
	 * @see NoteStore.Client#untagAll(String, String)
	 */
	void untagAll(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#expungeTag(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#expungeTag(String)
	 * @see NoteStore.Client#expungeTag(String, String)
	 */
	int expungeTag(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#listSearches()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#listSearches()
	 * @see NoteStore.Client#listSearches(String)
	 */
	List<SavedSearch> listSearches() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getSearch(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getSearch(String)
	 * @see NoteStore.Client#getSearch(String, String)
	 */
	SavedSearch getSearch(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#createSearch(com.evernote.edam.type.SavedSearch)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#createSearch(com.evernote.edam.type.SavedSearch)
	 * @see NoteStore.Client#createSearch(String, com.evernote.edam.type.SavedSearch)
	 */
	SavedSearch createSearch(SavedSearch search) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#updateSearch(com.evernote.edam.type.SavedSearch)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#updateSearch(com.evernote.edam.type.SavedSearch)
	 * @see NoteStore.Client#updateSearch(String, com.evernote.edam.type.SavedSearch)
	 */
	int updateSearch(SavedSearch search) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#expungeSearch(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#expungeSearch(String)
	 * @see NoteStore.Client#expungeSearch(String, String)
	 */
	int expungeSearch(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#findNotes(com.evernote.edam.notestore.NoteFilter, int, int)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#findNotes(com.evernote.edam.notestore.NoteFilter, int, int)
	 * @see NoteStore.Client#findNotes(String, com.evernote.edam.notestore.NoteFilter, int, int)
	 */
	NoteList findNotes(NoteFilter filter, int offset, int maxNotes) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#findNoteOffset(com.evernote.edam.notestore.NoteFilter, String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#findNoteOffset(com.evernote.edam.notestore.NoteFilter, String)
	 * @see NoteStore.Client#findNoteOffset(String, com.evernote.edam.notestore.NoteFilter, String)
	 */
	int findNoteOffset(NoteFilter filter, String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#findNotesMetadata(com.evernote.edam.notestore.NoteFilter, int, int, com.evernote.edam.notestore.NotesMetadataResultSpec)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#findNotesMetadata(com.evernote.edam.notestore.NoteFilter, int, int, com.evernote.edam.notestore.NotesMetadataResultSpec)
	 * @see NoteStore.Client#findNotesMetadata(String, com.evernote.edam.notestore.NoteFilter, int, int, com.evernote.edam.notestore.NotesMetadataResultSpec)
	 */
	NotesMetadataList findNotesMetadata(NoteFilter filter, int offset, int maxNotes, NotesMetadataResultSpec resultSpec)
			throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#findNoteCounts(com.evernote.edam.notestore.NoteFilter, boolean)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#findNoteCounts(com.evernote.edam.notestore.NoteFilter, boolean)
	 * @see NoteStore.Client#findNoteCounts(String, com.evernote.edam.notestore.NoteFilter, boolean)
	 */
	NoteCollectionCounts findNoteCounts(NoteFilter filter, boolean withTrash) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getNote(String, boolean, boolean, boolean, boolean)}
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getNote(String, boolean, boolean, boolean, boolean)
	 * @see NoteStore.Client#getNote(String, String, boolean, boolean, boolean, boolean)
	 */
	Note getNote(String guid, boolean withContent, boolean withResourcesData, boolean withResourcesRecognition,
	             boolean withResourcesAlternateData) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getNoteApplicationData(String)}
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getNoteApplicationData(String)
	 * @see NoteStore.Client#getNoteApplicationData(String, String)
	 */
	LazyMap getNoteApplicationData(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getNoteApplicationDataEntry(String, String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#
	 * @see NoteStore.Client#getNoteApplicationDataEntry(String, String, String)
	 */
	String getNoteApplicationDataEntry(String guid, String key) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#setNoteApplicationDataEntry(String, String, String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#setNoteApplicationDataEntry(String, String, String)
	 * @see NoteStore.Client#setNoteApplicationDataEntry(String, String, String, String)
	 */
	int setNoteApplicationDataEntry(String guid, String key, String value) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#unsetNoteApplicationDataEntry(String, String)}
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#unsetNoteApplicationDataEntry(String, String)
	 * @see NoteStore.Client#unsetNoteApplicationDataEntry(String, String, String)
	 */
	int unsetNoteApplicationDataEntry(String guid, String key) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getNoteContent(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getNoteContent(String)
	 * @see NoteStore.Client#getNoteContent(String, String)
	 */
	String getNoteContent(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getNoteSearchText(String, boolean, boolean)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getNoteSearchText(String, boolean, boolean)
	 * @see NoteStore.Client#getNoteSearchText(String, String, boolean, boolean)
	 */
	String getNoteSearchText(String guid, boolean noteOnly, boolean tokenizeForIndexing) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getResourceSearchText(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getResourceSearchText(String)
	 * @see NoteStore.Client#getResourceSearchText(String, String)
	 */
	String getResourceSearchText(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getNoteTagNames(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getNoteTagNames(String)
	 * @see NoteStore.Client#getNoteTagNames(String, String)
	 */
	List<String> getNoteTagNames(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#createNote(com.evernote.edam.type.Note)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#createNote(com.evernote.edam.type.Note)
	 * @see NoteStore.Client#createNote(String, com.evernote.edam.type.Note)
	 */
	Note createNote(Note note) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#updateNote(com.evernote.edam.type.Note)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#updateNote(com.evernote.edam.type.Note)
	 * @see NoteStore.Client#updateNote(String, com.evernote.edam.type.Note)
	 */
	Note updateNote(Note note) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#deleteNote(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#deleteNote(String)
	 * @see NoteStore.Client#deleteNote(String, String)
	 */
	int deleteNote(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#expungeNote(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#expungeNote(String)
	 * @see NoteStore.Client#expungeNote(String, String)
	 */
	int expungeNote(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#expungeNotes(java.util.List)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#expungeNotes(java.util.List)
	 * @see NoteStore.Client#expungeNotes(String, java.util.List)
	 */
	int expungeNotes(List<String> noteGuids) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#expungeInactiveNotes()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#expungeInactiveNotes()
	 * @see NoteStore.Client#expungeInactiveNotes(String)
	 */
	int expungeInactiveNotes() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#copyNote(String, String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#copyNote(String, String)
	 * @see NoteStore.Client#copyNote(String, String, String)
	 */
	Note copyNote(String noteGuid, String toNotebookGuid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#listNoteVersions(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#listNoteVersions(String)
	 * @see NoteStore.Client#listNoteVersions(String, String)
	 */
	List<NoteVersionId> listNoteVersions(String noteGuid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getNoteVersion(String, int, boolean, boolean, boolean)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getNoteVersion(String, int, boolean, boolean, boolean)
	 * @see NoteStore.Client#getNoteVersion(String, String, int, boolean, boolean, boolean)
	 */
	Note getNoteVersion(String noteGuid, int updateSequenceNum, boolean withResourcesData,
	                    boolean withResourcesRecognition, boolean withResourcesAlternateData) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getResource(String, boolean, boolean, boolean, boolean)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getResource(String, boolean, boolean, boolean, boolean)
	 * @see NoteStore.Client#getResource(String, String, boolean, boolean, boolean, boolean)
	 */
	Resource getResource(String guid, boolean withData, boolean withRecognition, boolean withAttributes,
	                     boolean withAlternateData) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getResourceApplicationData(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getResourceApplicationData(String)
	 * @see NoteStore.Client#getResourceApplicationData(String, String)
	 */
	LazyMap getResourceApplicationData(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getResourceApplicationDataEntry(String, String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getResourceApplicationDataEntry(String, String)
	 * @see NoteStore.Client#getResourceApplicationDataEntry(String, String, String)
	 */
	String getResourceApplicationDataEntry(String guid, String key) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#setResourceApplicationDataEntry(String, String, String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#setResourceApplicationDataEntry(String, String, String)
	 * @see NoteStore.Client#setResourceApplicationDataEntry(String, String, String, String)
	 */
	int setResourceApplicationDataEntry(String guid, String key, String value) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#unsetResourceApplicationDataEntry(String, String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#unsetResourceApplicationDataEntry(String, String)
	 * @see NoteStore.Client#unsetResourceApplicationDataEntry(String, String, String)
	 */
	int unsetResourceApplicationDataEntry(String guid, String key) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#updateResource(com.evernote.edam.type.Resource)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#updateResource(com.evernote.edam.type.Resource)
	 * @see NoteStore.Client#updateResource(String, com.evernote.edam.type.Resource)
	 */
	int updateResource(Resource resource) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getResourceData(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getResourceData(String)
	 * @see NoteStore.Client#getResourceData(String, String)
	 */
	byte[] getResourceData(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getResourceByHash(String, byte[], boolean, boolean, boolean)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getResourceByHash(String, byte[], boolean, boolean, boolean)
	 * @see NoteStore.Client#getResourceByHash(String, String, byte[], boolean, boolean, boolean)
	 */
	Resource getResourceByHash(String noteGuid, byte[] contentHash, boolean withData, boolean withRecognition,
	                           boolean withAlternateData) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getResourceRecognition(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getResourceRecognition(String)
	 * @see NoteStore.Client#getResourceRecognition(String, String)
	 */
	byte[] getResourceRecognition(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getResourceAlternateData(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getResourceAlternateData(String)
	 * @see NoteStore.Client#getResourceAlternateData(String, String)
	 */
	byte[] getResourceAlternateData(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getResourceAttributes(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getResourceAttributes(String)
	 * @see NoteStore.Client#getResourceAttributes(String, String)
	 */
	ResourceAttributes getResourceAttributes(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getPublicNotebook(int, String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getPublicNotebook(int, String)
	 * @see NoteStore.Client#getPublicNotebook(int, String)
	 */
	Notebook getPublicNotebook(int userId, String publicUri) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#createSharedNotebook(com.evernote.edam.type.SharedNotebook)}
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#createSharedNotebook(com.evernote.edam.type.SharedNotebook)
	 * @see NoteStore.Client#createSharedNotebook(String, com.evernote.edam.type.SharedNotebook)
	 */
	SharedNotebook createSharedNotebook(SharedNotebook sharedNotebook) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#updateSharedNotebook(com.evernote.edam.type.SharedNotebook)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#updateSharedNotebook(com.evernote.edam.type.SharedNotebook)
	 * @see NoteStore.Client#updateSharedNotebook(String, com.evernote.edam.type.SharedNotebook)
	 */
	int updateSharedNotebook(SharedNotebook sharedNotebook) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#sendMessageToSharedNotebookMembers(String, String, java.util.List)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#sendMessageToSharedNotebookMembers(String, String, java.util.List)
	 * @see NoteStore.Client#sendMessageToSharedNotebookMembers(String, String, String, java.util.List)
	 */
	int sendMessageToSharedNotebookMembers(String notebookGuid, String messageText, List<String> recipients)
			throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#listSharedNotebooks()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#listSharedNotebooks()
	 * @see NoteStore.Client#listSharedNotebooks(String)
	 */
	List<SharedNotebook> listSharedNotebooks() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#expungeSharedNotebooks(java.util.List)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#expungeSharedNotebooks(java.util.List)
	 * @see NoteStore.Client#expungeSharedNotebooks(String, java.util.List)
	 */
	int expungeSharedNotebooks(List<Long> sharedNotebookIds) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#createLinkedNotebook(com.evernote.edam.type.LinkedNotebook)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#createLinkedNotebook(com.evernote.edam.type.LinkedNotebook)
	 * @see NoteStore.Client#createLinkedNotebook(String, com.evernote.edam.type.LinkedNotebook)
	 */
	LinkedNotebook createLinkedNotebook(LinkedNotebook linkedNotebook) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#updateLinkedNotebook(com.evernote.edam.type.LinkedNotebook)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#updateLinkedNotebook(com.evernote.edam.type.LinkedNotebook)
	 * @see NoteStore.Client#updateLinkedNotebook(String, com.evernote.edam.type.LinkedNotebook)
	 */
	int updateLinkedNotebook(LinkedNotebook linkedNotebook) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#listLinkedNotebooks()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#listLinkedNotebooks()
	 * @see NoteStore.Client#listLinkedNotebooks(String)
	 */
	List<LinkedNotebook> listLinkedNotebooks() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#expungeLinkedNotebook(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#expungeLinkedNotebook(String)
	 * @see NoteStore.Client#expungeLinkedNotebook(String, String)
	 */
	int expungeLinkedNotebook(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#authenticateToSharedNotebook(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#authenticateToSharedNotebook(String)
	 * @see NoteStore.Client#authenticateToSharedNotebook(String, String)
	 */
	AuthenticationResult authenticateToSharedNotebook(String shareKey) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#getSharedNotebookByAuth()}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#getSharedNotebookByAuth()
	 * @see NoteStore.Client#getSharedNotebookByAuth(String)
	 */
	SharedNotebook getSharedNotebookByAuth() throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#emailNote(com.evernote.edam.notestore.NoteEmailParameters)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#emailNote(com.evernote.edam.notestore.NoteEmailParameters)
	 * @see NoteStore.Client#emailNote(String, com.evernote.edam.notestore.NoteEmailParameters)
	 */
	void emailNote(NoteEmailParameters parameters) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#shareNote(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#shareNote(String)
	 * @see NoteStore.Client#shareNote(String, String)
	 */
	String shareNote(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#stopSharingNote(String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#stopSharingNote
	 * @see NoteStore.Client#stopSharingNote(String, String)
	 */
	void stopSharingNote(String guid) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#authenticateToSharedNote(String, String, String)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#authenticateToSharedNote(String, String, String)
	 * @see NoteStore.Client#authenticateToSharedNote(String, String, String)
	 */
	AuthenticationResult authenticateToSharedNote(String guid, String noteKey, String authenticationToken)
			throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#findRelated(com.evernote.edam.notestore.RelatedQuery, com.evernote.edam.notestore.RelatedResultSpec)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#findRelated(com.evernote.edam.notestore.RelatedQuery, com.evernote.edam.notestore.RelatedResultSpec)
	 * @see NoteStore.Client#findRelated(String, com.evernote.edam.notestore.RelatedQuery, com.evernote.edam.notestore.RelatedResultSpec)
	 */
	RelatedResult findRelated(RelatedQuery query, RelatedResultSpec resultSpec) throws EvernoteException;

	/**
	 * Equivalent to {@link com.evernote.clients.NoteStoreClient#setSharedNotebookRecipientSettings(String, long, com.evernote.edam.type.SharedNotebookRecipientSettings)}.
	 *
	 * @throws EvernoteException converted unchecked exception
	 * @see com.evernote.clients.NoteStoreClient#setSharedNotebookRecipientSettings(String, long, com.evernote.edam.type.SharedNotebookRecipientSettings)
	 * @see NoteStore.Client#setSharedNotebookRecipientSettings(String, long, SharedNotebookRecipientSettings)
	 */
	void setSharedNotebookRecipientSettings(String authenticationToken, long sharedNotebookId,
	                                        SharedNotebookRecipientSettings recipientSettings) throws EvernoteException;

}
