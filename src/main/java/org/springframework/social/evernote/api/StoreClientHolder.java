package org.springframework.social.evernote.api;

/**
 * @author Tadaya Tsuyukubo
 */
public interface StoreClientHolder<T> {
	T getStoreClient();
}
