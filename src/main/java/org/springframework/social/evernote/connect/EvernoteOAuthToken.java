package org.springframework.social.evernote.connect;

import org.springframework.social.oauth1.OAuthToken;

/**
 * Sample access-token response params:
 * {oauth_token=[....],
 * oauth_token_secret=[],
 * edam_shard=[s1],
 * edam_userId=[123456],
 * edam_expires=[1418253288396],
 * edam_noteStoreUrl=[https://sandbox.evernote.com/shard/s1/notestore],
 * edam_webApiUrlPrefix=[https://sandbox.evernote.com/shard/s1/]}
 *
 * @author Tadaya Tsuyukubo
 */
public class EvernoteOAuthToken extends OAuthToken {

	public static final String KEY_OAUTH_TOKEN = "oauth_token";
	public static final String KEY_OAUTH_TOKEN_SECRET = "oauth_token_secret";
	public static final String EDAM_SHARD = "edam_shard";
	public static final String EDAM_USER_ID = "edam_userId";
	public static final String EDAM_EXPIRES = "edam_expires";
	public static final String EDAM_NOTE_STORE_URL = "edam_noteStoreUrl";
	public static final String EDAM_WEB_API_URL_PREFIX = "edam_webApiUrlPrefix";


	private String edamShard;
	private String edamUserId;
	private String edamExpires;
	private String edamNoteStoreUrl;
	private String edamWebApiUrlPrefix;

	private EvernoteOAuthToken(String value, String secret) {
		super(value, secret);
	}

	public String getEdamShard() {
		return edamShard;
	}

	public String getEdamUserId() {
		return edamUserId;
	}

	public String getEdamExpires() {
		return edamExpires;
	}

	public String getEdamNoteStoreUrl() {
		return edamNoteStoreUrl;
	}

	public String getEdamWebApiUrlPrefix() {
		return edamWebApiUrlPrefix;
	}

	public static class EvernoteOAuthTokenBuilder {
		private String value;
		private String secret;
		private String edamShard;
		private String edamUserId;
		private String edamExpires;
		private String edamNoteStoreUrl;
		private String edamWebApiUrlPrefix;

		public EvernoteOAuthToken build() {
			// TODO: assert null

			EvernoteOAuthToken token = new EvernoteOAuthToken(this.value, this.secret);
			token.edamShard = this.edamShard;
			token.edamUserId = this.edamUserId;
			token.edamExpires = this.edamExpires;
			token.edamNoteStoreUrl = this.edamNoteStoreUrl;
			token.edamWebApiUrlPrefix = this.edamWebApiUrlPrefix;
			return token;
		}

		public EvernoteOAuthTokenBuilder setToken(String value) {
			this.value = value;
			return this;
		}

		public EvernoteOAuthTokenBuilder setSecret(String secret) {
			this.secret = secret;
			return this;
		}

		public EvernoteOAuthTokenBuilder setEdamShard(String edamShard) {
			this.edamShard = edamShard;
			return this;
		}

		public EvernoteOAuthTokenBuilder setEdamUserId(String edamUserId) {
			this.edamUserId = edamUserId;
			return this;
		}

		public EvernoteOAuthTokenBuilder setEdamExpires(String edamExpires) {
			this.edamExpires = edamExpires;
			return this;
		}

		public EvernoteOAuthTokenBuilder setEdamNoteStoreUrl(String edamNoteStoreUrl) {
			this.edamNoteStoreUrl = edamNoteStoreUrl;
			return this;
		}

		public EvernoteOAuthTokenBuilder setEdamWebApiUrlPrefix(String edamWebApiUrlPrefix) {
			this.edamWebApiUrlPrefix = edamWebApiUrlPrefix;
			return this;
		}
	}
}
