package org.springframework.social.evernote.api.impl;

import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.social.evernote.api.impl.entity.Bar;
import org.springframework.social.evernote.api.impl.entity.Baz;
import org.springframework.social.evernote.api.impl.entity.Foo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.social.evernote.api.impl.ThriftWrapper.makeNullSafe;
import static org.springframework.social.evernote.api.impl.ThriftWrapper.unwrap;

/**
 * @author Tadaya Tsuyukubo
 */
public class ThriftWrapperUnwrapTest {

	@Test
	public void testUnwrap() throws Exception {
		Foo foo = new Foo("foo", new ArrayList<String>(), new HashSet<String>(), new HashMap<String, String>());
		Bar bar = new Bar();
		Baz baz = new Baz();
		bar.setName("bar");
		baz.setName("baz");
		bar.setBaz(baz);
		foo.setBar(bar);

		Foo wrapped = makeNullSafe(foo);  // wrap
		Foo unwrapped = unwrap(wrapped);

		// initially all options were null
		assertThat(AopUtils.isAopProxy(unwrapped), is(false));
		assertThat(unwrapped.getRequiredList(), is(emptyCollectionOf(String.class)));
		assertThat(unwrapped.getRequiredSet(), is(emptyCollectionOf(String.class)));
		assertThat(unwrapped.getRequiredMap(), is(notNullValue()));
		assertThat(unwrapped.getRequiredMap().size(), is(0));
		assertThat(unwrapped.getOptionalList(), is(nullValue()));
		assertThat(unwrapped.getOptionalSet(), is(nullValue()));
		assertThat(unwrapped.getOptionalMap(), is(nullValue()));

		assertThat(AopUtils.isAopProxy(unwrapped.getBar()), is(false));
		assertThat(unwrapped.getBar().getName(), is("bar"));
		assertThat(unwrapped.getBar().getBarList(), is(nullValue()));
		assertThat(unwrapped.getBar().getBarSet(), is(nullValue()));
		assertThat(unwrapped.getBar().getBarMap(), is(nullValue()));

		assertThat(AopUtils.isAopProxy(unwrapped.getBar().getBaz()), is(false));
		assertThat(unwrapped.getBar().getBaz().getName(), is("baz"));
		assertThat(unwrapped.getBar().getBaz().getBazList(), is(nullValue()));
		assertThat(unwrapped.getBar().getBaz().getBazSet(), is(nullValue()));
		assertThat(unwrapped.getBar().getBaz().getBazMap(), is(nullValue()));
	}

	@Test
	public void testUnwrapWithAddedValues() throws Exception {
		// when initially null, but something has already added
		Foo foo = new Foo("foo", new ArrayList<String>(), new HashSet<String>(), new HashMap<String, String>());

		Foo wrapped = makeNullSafe(foo);  // wrap
		wrapped.addToOptionalList("FOO");
		wrapped.addToOptionalSet("FOO");
		wrapped.putToOptionalMap("FOO-KEY", "FOO-VALUE");

		Foo unwrapped = unwrap(wrapped);  // unwrap

		// should retain added values
		assertThat(unwrapped.getOptionalList(), is(hasItem("FOO")));
		assertThat(unwrapped.getOptionalSet(), is(hasItem("FOO")));
		assertThat(unwrapped.getOptionalMap(), is(hasEntry("FOO-KEY", "FOO-VALUE")));
	}

	@Test
	public void testUnwrapNullValue() {
		assertThat(ThriftWrapper.unwrap(null), is(nullValue()));
	}

}
