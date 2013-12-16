package org.springframework.social.evernote.connect;

import com.evernote.edam.type.User;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.evernote.api.Evernote;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteAdapter implements ApiAdapter<Evernote> {
	@Override
	public boolean test(Evernote evernote) {
		try {
			evernote.userStoreClient().getUser();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void setConnectionValues(Evernote evernote, ConnectionValues values) {
		// this impl requires another call to server.
		// TODO: get data from EvernoteAuthToken??
		final User user = evernote.userStoreClientOperations().getUser();
		values.setProviderUserId(String.valueOf(user.getId()));  // can get from EvernoteAuthToken
		values.setDisplayName(user.getUsername());
		values.setProfileUrl(null);
		values.setImageUrl(null);
	}

	@Override
	public UserProfile fetchUserProfile(Evernote evernote) {
		final User user = evernote.userStoreClientOperations().getUser();
		return new UserProfileBuilder()
				.setName(user.getName())
				.setEmail(user.getEmail())
				.setUsername(user.getUsername())
				.build();
	}

	@Override
	public void updateStatus(Evernote evernote, String message) {
		// TODO: no-op??
	}
}
