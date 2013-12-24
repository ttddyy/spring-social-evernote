package org.springframework.social.evernote.api.impl;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.social.evernote.api.impl.ThriftWrapper.makeNullSafe;

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


		CollectionHolder result = makeNullSafe(holder);
		assertThat(result.getCollection(), hasSize(1));
		assertThat(result.getList(), hasSize(1));
		assertThat(result.getSet(), hasSize(1));

		CollectionHolder elementCollection = result.getCollection().iterator().next();
		CollectionHolder elementList = result.getList().iterator().next();
		CollectionHolder elementSet = result.getSet().iterator().next();

		assertThat(elementCollection.getCollection(), is(notNullValue()));
		assertThat(elementCollection.getCollection(), is(emptyCollectionOf(CollectionHolder.class)));

		assertThat(elementList.getCollection(), is(notNullValue()));
		assertThat(elementList.getCollection(), is(emptyCollectionOf(CollectionHolder.class)));

		assertThat(elementSet.getCollection(), is(notNullValue()));
		assertThat(elementSet.getCollection(), is(emptyCollectionOf(CollectionHolder.class)));
	}
}
