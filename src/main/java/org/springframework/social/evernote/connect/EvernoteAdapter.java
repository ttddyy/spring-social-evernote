package org.springframework.social.evernote.connect;

import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.type.User;
import com.evernote.thrift.TException;
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
		// TODO: impl
//        evernote.userStoreClient().getUser().getId()  // provider user id
//        evernote.userStoreClient().getUser().getUsername()  // display name

//        values.setProviderUserId();
//        values.setProfileUrl();  // null
//        values.setDisplayName();
//        values.setImageUrl();  // null
	}

	@Override
	public UserProfile fetchUserProfile(Evernote evernote) {
		// TODO: use wrapped operations
		try {
			final User user = evernote.userStoreClient().getUser();
			return new UserProfileBuilder()
					.setName(user.getName())
					.setEmail(user.getEmail())
					.setUsername(user.getUsername())
					.build();
		} catch (Exception e) {
			return UserProfile.EMPTY;
		}
	}

	@Override
	public void updateStatus(Evernote evernote, String message) {
		// TODO: no-op??
	}
}
