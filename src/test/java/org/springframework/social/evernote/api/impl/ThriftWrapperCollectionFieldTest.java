package org.springframework.social.evernote.api.impl;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.social.evernote.api.impl.ThriftWrapper.makeNullSafe;
import static org.springframework.social.evernote.api.impl.ThriftWrapper.unwrap;

/**
 * @author Tadaya Tsuyukubo
 */
public class ThriftWrapperCollectionFieldTest {

	public static class CollectionHolder {
		private Collection<CollectionHolder> collection;
		private List<CollectionHolder> list;
		private Set<CollectionHolder> set;

		// for cglib to create a proxy
		public CollectionHolder() {
		}

		public Collection<CollectionHolder> getCollection() {
			return collection;
		}

		public List<CollectionHolder> getList() {
			return list;
		}

		public Set<CollectionHolder> getSet() {
			return set;
		}
	}

	@Test
	public void testElementsInCollection() {
		Collection<CollectionHolder> internalCollection = new ArrayList<CollectionHolder>();
		List<CollectionHolder> internalList = new ArrayList<CollectionHolder>();
		Set<CollectionHolder> internalSet = new HashSet<CollectionHolder>();

		// set a holder which contains null collection, list, and set.
		internalCollection.add(new CollectionHolder());
		internalList.add(new CollectionHolder());
		internalSet.add(new CollectionHolder());

		CollectionHolder holder = new CollectionHolder();
		holder.collection = internalCollection;
		holder.list = internalList;
		holder.set = internalSet;


		CollectionHolder wrapped = makeNullSafe(holder);
		assertThat(wrapped.getCollection(), hasSize(1));
		assertThat(wrapped.getList(), hasSize(1));
		assertThat(wrapped.getSet(), hasSize(1));

		CollectionHolder elementInCollection = wrapped.getCollection().iterator().next();
		CollectionHolder elementInList = wrapped.getList().iterator().next();
		CollectionHolder elementInSet = wrapped.getSet().iterator().next();

		assertThat(elementInCollection.getCollection(), is(notNullValue()));
		assertThat(elementInCollection.getCollection(), is(emptyCollectionOf(CollectionHolder.class)));

		assertThat(elementInList.getCollection(), is(notNullValue()));
		assertThat(elementInList.getCollection(), is(emptyCollectionOf(CollectionHolder.class)));

		assertThat(elementInSet.getCollection(), is(notNullValue()));
		assertThat(elementInSet.getCollection(), is(emptyCollectionOf(CollectionHolder.class)));

		// unwrapped
		CollectionHolder unwrapped = unwrap(wrapped);
		assertThat(unwrapped.getCollection(), hasSize(1));
		assertThat(unwrapped.getList(), hasSize(1));
		assertThat(unwrapped.getSet(), hasSize(1));

		CollectionHolder elementInUnwrappedCollection = unwrapped.getCollection().iterator().next();
		CollectionHolder elementInUnwrappedList = unwrapped.getList().iterator().next();
		CollectionHolder elementInUnwrappedSet = unwrapped.getSet().iterator().next();

		assertThat(elementInUnwrappedCollection.getCollection(), is(nullValue()));
		assertThat(elementInUnwrappedList.getCollection(), is(nullValue()));
		assertThat(elementInUnwrappedSet.getCollection(), is(nullValue()));
	}
}
