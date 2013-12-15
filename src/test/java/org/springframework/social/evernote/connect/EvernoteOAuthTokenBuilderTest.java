package org.springframework.social.evernote.connect;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteOAuthTokenBuilderTest {

	@Test
	public void testBuild() {
		EvernoteOAuthToken.EvernoteOAuthTokenBuilder builder = new EvernoteOAuthToken.EvernoteOAuthTokenBuilder();
		builder.setToken("token");
		builder.setSecret("secret");
		builder.setEdamUserId("userId");
		builder.setEdamShard("shard");
		builder.setEdamExpires("expires");
		builder.setEdamNoteStoreUrl("noteStoreUrl");
		builder.setEdamWebApiUrlPrefix("webApiUrlPrefix");

		EvernoteOAuthToken token = builder.build();
		assertThat(token.getValue(), is("token"));
		assertThat(token.getSecret(), is("secret"));
		assertThat(token.getEdamUserId(), is("userId"));
		assertThat(token.getEdamShard(), is("shard"));
		assertThat(token.getEdamExpires(), is("expires"));
		assertThat(token.getEdamNoteStoreUrl(), is("noteStoreUrl"));
		assertThat(token.getEdamWebApiUrlPrefix(), is("webApiUrlPrefix"));

	}
}
