package org.springframework.social.evernote.api.impl;

import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;
import org.springframework.social.evernote.api.impl.entity.Bar;
import org.springframework.social.evernote.api.impl.entity.Baz;
import org.springframework.social.evernote.api.impl.entity.Foo;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.social.evernote.api.Impl.ThriftWrapper.makeNullSafe;

/**
 * @author Tadaya Tsuyukubo
 */
public class ThriftWrapperTest {
	@Test
	public void testWrap() throws Exception {
		Foo foo = new Foo("foo", new ArrayList<String>(), new HashSet<String>(), new HashMap<String, String>());
		Bar bar = new Bar();
		Baz baz = new Baz();
		bar.setName("bar");
		baz.setName("baz");
		bar.setBaz(baz);
		foo.setBar(bar);

		// initially all options are null
		assertThat(foo.getName(), is("foo"));
		assertThat(foo.getRequiredList(), is(emptyCollectionOf(String.class)));
		assertThat(foo.getRequiredSet(), is(emptyCollectionOf(String.class)));
		assertThat(foo.getRequiredMap(), is(notNullValue()));
		assertThat(foo.getRequiredMap().size(), is(0));
		assertThat(foo.getOptionalList(), is(nullValue()));
		assertThat(foo.getOptionalSet(), is(nullValue()));
		assertThat(foo.getOptionalMap(), is(nullValue()));
		assertThat(bar.getName(), is("bar"));
		assertThat(bar.getBarList(), is(nullValue()));
		assertThat(bar.getBarSet(), is(nullValue()));
		assertThat(bar.getBarMap(), is(nullValue()));
		assertThat(baz.getName(), is("baz"));
		assertThat(baz.getBazList(), is(nullValue()));
		assertThat(baz.getBazSet(), is(nullValue()));
		assertThat(baz.getBazMap(), is(nullValue()));

		Foo wrapped = makeNullSafe(foo);  // wrap
		assertThat(wrapped.getName(), is("foo"));
		assertThat(wrapped.getRequiredList(), is(emptyCollectionOf(String.class)));
		assertThat(wrapped.getRequiredSet(), is(emptyCollectionOf(String.class)));
		assertThat(wrapped.getRequiredMap(), is(notNullValue()));
		assertThat(wrapped.getRequiredMap().size(), is(0));
		assertThat(wrapped.getOptionalList(), is(emptyCollectionOf(String.class)));
		assertThat(wrapped.getOptionalSet(), is(emptyCollectionOf(String.class)));
		assertThat(wrapped.getOptionalMap(), is(notNullValue()));
		assertThat(wrapped.getOptionalMap().size(), is(0));
		assertThat(wrapped.getBar().getName(), is("bar"));
		assertThat(wrapped.getBar().getBarList(), is(emptyCollectionOf(String.class)));
		assertThat(wrapped.getBar().getBarSet(), is(emptyCollectionOf(String.class)));
		assertThat(wrapped.getBar().getBarMap(), is(notNullValue()));
		assertThat(wrapped.getBar().getBarMap().size(), is(0));
		assertThat(wrapped.getBar().getBaz().getName(), is("baz"));
		assertThat(wrapped.getBar().getBaz().getBazList(), is(emptyCollectionOf(String.class)));
		assertThat(wrapped.getBar().getBaz().getBazSet(), is(emptyCollectionOf(String.class)));
		assertThat(wrapped.getBar().getBaz().getBazMap(), is(notNullValue()));
		assertThat(wrapped.getBar().getBaz().getBazMap().size(), is(0));

	}

	@Test
	public void testWrite() throws Exception {
		TTransport tTransport = new TMemoryBuffer(100);
		TProtocol tProtocol = new TJSONProtocol(tTransport);

		Foo foo = new Foo("foo", new ArrayList<String>(), new HashSet<String>(), new HashMap<String, String>());
		Bar bar = new Bar();
		Baz baz = new Baz();
		bar.setName("bar");
		baz.setName("baz");
		foo.setBar(bar);
		bar.setBaz(baz);

		// optional fields are all null, so should not be transmitted
		Foo wrapped = makeNullSafe(foo);
		wrapped.write(tProtocol);


		List<String> list = new ArrayList<String>(Arrays.asList(new String[]{"LIST-VALUE"}));
		Set<String> set = new HashSet<String>(Arrays.asList(new String[]{"SET-VALUE"}));
		Map<String, String> map = new HashMap<String, String>();
		map.put("MAP-KEY", "MAP-VALUE");

		// construct new object graph with some values in collections
		Foo newFoo = new Foo("FOO", list, set, map);
		newFoo.setOptionalList(list);
		newFoo.setOptionalSet(set);
		newFoo.setOptionalMap(map);
		Bar newBar = new Bar();
		newBar.setName("BAR");
		newBar.setBarList(list);
		newBar.setBarSet(set);
		newBar.setBarMap(map);
		newFoo.setBar(newBar);

		// read from message
		newFoo.read(tProtocol);

		assertThat(newFoo.getName(), is("foo"));
		assertThat(newFoo.getRequiredList(), is(emptyCollectionOf(String.class)));
		assertThat(newFoo.getRequiredSet(), is(emptyCollectionOf(String.class)));
		assertThat(newFoo.getRequiredMap(), is(notNullValue()));
		assertThat(newFoo.getRequiredMap().size(), is(0));
		// optional fields should not be overridden because they were not sent in thrift message
		assertThat(newFoo.getOptionalList(), hasItem("LIST-VALUE"));
		assertThat(newFoo.getOptionalSet(), hasItem("SET-VALUE"));
		assertThat(newFoo.getOptionalMap(), hasEntry("MAP-KEY", "MAP-VALUE"));

		assertThat(newFoo.getBar(), is(not(sameInstance(bar)))); // thrift creates new instance
		assertThat(newFoo.getBar().getName(), is("bar"));
		assertThat(newFoo.getBar().getBarList(), is(nullValue()));  // default for optional container is null
		assertThat(newFoo.getBar().getBarSet(), is(nullValue()));
		assertThat(newFoo.getBar().getBarMap(), is(nullValue()));

		assertThat(newFoo.getBar().getBaz(), is(not(sameInstance(baz))));
		assertThat(newFoo.getBar().getBaz().getName(), is("baz"));
		assertThat(newFoo.getBar().getBaz().getBazList(), is(nullValue()));
		assertThat(newFoo.getBar().getBaz().getBazSet(), is(nullValue()));
		assertThat(newFoo.getBar().getBaz().getBazMap(), is(nullValue()));

	}


	@Test
	public void testSetValue() throws Exception {
		Foo foo = new Foo("foo", new ArrayList<String>(), new HashSet<String>(), new HashMap<String, String>());
		Foo wrapped = makeNullSafe(foo);

		wrapped.getOptionalList().add("FOO-LIST");
		wrapped.getOptionalSet().add("FOO-SET");
		wrapped.getOptionalMap().put("FOO-MAP-KEY", "FOO-MAP-VALUE");

		assertThat(foo.getOptionalList(), hasItem("FOO-LIST"));
		assertThat(foo.getOptionalSet(), hasItem("FOO-SET"));
		assertThat(foo.getOptionalMap(), hasEntry("FOO-MAP-KEY", "FOO-MAP-VALUE"));
		assertThat(wrapped.getOptionalList(), hasItem("FOO-LIST"));
		assertThat(wrapped.getOptionalSet(), hasItem("FOO-SET"));
		assertThat(wrapped.getOptionalMap(), hasEntry("FOO-MAP-KEY", "FOO-MAP-VALUE"));

	}

	@Test
	public void testWriteWithValue() throws Exception {
		TTransport tTransport = new TMemoryBuffer(100);
		TProtocol tProtocol = new TJSONProtocol(tTransport);

		Foo foo = new Foo("foo", new ArrayList<String>(), new HashSet<String>(), new HashMap<String, String>());
		foo.addToOptionalList("FOO-LIST");
		foo.addToOptionalSet("FOO-SET");
		foo.putToOptionalMap("FOO-MAP-KEY", "FOO-MAP-VALUE");
		Bar bar = new Bar();
		bar.setName("bar");
		bar.addToBarList("BAR-LIST");
		bar.addToBarSet("BAR-SET");
		bar.putToBarMap("BAR-MAP-KEY", "BAR-MAP-VALUE");
		foo.setBar(bar);

		Foo wrapped = makeNullSafe(foo);
		wrapped.write(tProtocol);

		Foo newFoo = new Foo("new-foo", new ArrayList<String>(), new HashSet<String>(), new HashMap<String, String>());

		// read from message
		newFoo.read(tProtocol);

		assertThat(newFoo.getOptionalList(), hasItem("FOO-LIST"));
		assertThat(newFoo.getOptionalSet(), hasItem("FOO-SET"));
		assertThat(newFoo.getOptionalMap(), hasEntry("FOO-MAP-KEY", "FOO-MAP-VALUE"));
		assertThat(newFoo.getBar(), is(notNullValue()));
		assertThat(newFoo.getBar().getName(), is("bar"));
		assertThat(newFoo.getBar().getBarList(), hasItem("BAR-LIST"));
		assertThat(newFoo.getBar().getBarSet(), hasItem("BAR-SET"));
		assertThat(newFoo.getBar().getBarMap(), hasEntry("BAR-MAP-KEY", "BAR-MAP-VALUE"));
		assertThat(newFoo.getBar().getBaz(), is(nullValue()));
	}

	@Test
	public void testWriteSetCollectionsToEmpty() throws Exception {
		TTransport tTransport = new TMemoryBuffer(100);
		TProtocol tProtocol = new TJSONProtocol(tTransport);

		// original object has values in collections
		Foo foo = new Foo("foo", new ArrayList<String>(), new HashSet<String>(), new HashMap<String, String>());
		foo.addToOptionalList("FOO-LIST");
		foo.addToOptionalSet("FOO-SET");
		foo.putToOptionalMap("FOO-MAP-KEY", "FOO-MAP-VALUE");

		// wrap it
		Foo wrapped = makeNullSafe(foo);

		// make collections empty
		wrapped.getOptionalList().clear();
		wrapped.getOptionalSet().clear();
		wrapped.getOptionalMap().clear();

		// write
		wrapped.write(tProtocol);

		Foo newFoo = new Foo("new-foo", new ArrayList<String>(), new HashSet<String>(), new HashMap<String, String>());

		// read from message
		newFoo.read(tProtocol);

		// now new object should have empty collections
		assertThat(newFoo.getName(), is("foo"));
		assertThat(newFoo.getOptionalList(), is(emptyCollectionOf(String.class)));
		assertThat(newFoo.getOptionalSet(), is(emptyCollectionOf(String.class)));
		assertThat(newFoo.getOptionalMap(), is(notNullValue()));
		assertThat(newFoo.getOptionalMap().size(), is(0));

	}

	@Test
	public void testNull() {
		Object result = makeNullSafe(null);
		assertThat(result, is(nullValue()));
	}
}
