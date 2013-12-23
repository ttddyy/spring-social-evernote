package org.springframework.social.evernote.api;

import org.springframework.aop.RawTargetAccess;

/**
 * @author Tadaya Tsuyukubo
 */
public interface StoreClientHolder extends RawTargetAccess {
	<T> T getStoreClient();
}
