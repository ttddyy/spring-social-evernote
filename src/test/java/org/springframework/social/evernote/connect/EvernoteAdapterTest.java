package org.springframework.social.evernote.connect;

import com.evernote.clients.UserStoreClient;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.type.User;
import org.junit.Test;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.evernote.api.Evernote;
import org.springframework.social.evernote.api.UserStoreOperations;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteAdapterTest {

	@Test
	public void testTestValid() throws Exception {
		// test case when connection is valid
		Evernote evernote = mock(Evernote.class);
		UserStoreClient userStoreClient = mock(UserStoreClient.class);
		when(evernote.userStoreClient()).thenReturn(userStoreClient);
		when(userStoreClient.getUser()).thenReturn(new User());  // has some user

		EvernoteAdapter adapter = new EvernoteAdapter();
		boolean result = adapter.test(evernote);
		assertThat(result, is(true));
	}

	@Test
	public void testTestInvalid() throws Exception {
		// test case when connection is invalid
		Evernote evernote = mock(Evernote.class);
		UserStoreClient userStoreClient = mock(UserStoreClient.class);
		when(evernote.userStoreClient()).thenReturn(userStoreClient);
		when(userStoreClient.getUser()).thenThrow(EDAMUserException.class);

		EvernoteAdapter adapter = new EvernoteAdapter();
		boolean result = adapter.test(evernote);
		assertThat(result, is(false));
	}


	@Test
	public void testSetConnectionValues() {
		Evernote evernote = mock(Evernote.class);
		UserStoreOperations userStoreOperations = mock(UserStoreOperations.class);
		User user = new User();
		user.setId(100);
		user.setUsername("foo");

		when(evernote.userStoreOperations()).thenReturn(userStoreOperations);
		when(userStoreOperations.getUser()).thenReturn(user);

		EvernoteAdapter adapter = new EvernoteAdapter();
		ConnectionValues values = mock(ConnectionValues.class);
		adapter.setConnectionValues(evernote, values);

		verify(values).setProviderUserId("100");
		verify(values).setDisplayName("foo");
		verify(values).setImageUrl(null);
		verify(values).setProfileUrl(null);
	}

	@Test
	public void testFetchUserProfile() {
		Evernote evernote = mock(Evernote.class);
		UserStoreOperations userStoreOperations = mock(UserStoreOperations.class);
		User user = new User();
		user.setName("foo");
		user.setUsername("FOO");
		user.setEmail("foo@foo.com");

		when(evernote.userStoreOperations()).thenReturn(userStoreOperations);
		when(userStoreOperations.getUser()).thenReturn(user);

		EvernoteAdapter adapter = new EvernoteAdapter();
		UserProfile userProfile = adapter.fetchUserProfile(evernote);

		assertThat(userProfile.getName(), is("foo"));
		assertThat(userProfile.getUsername(), is("FOO"));
		assertThat(userProfile.getEmail(), is("foo@foo.com"));
	}
}
