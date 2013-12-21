package org.springframework.social.evernote.api.impl;

import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.social.evernote.api.impl.entity.Bar;
import org.springframework.social.evernote.api.impl.entity.Baz;
import org.springframework.social.evernote.api.impl.entity.Foo;
import org.springframework.util.ReflectionUtils;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.social.evernote.api.Impl.ThriftWrapper.makeNullSafe;

/**
 * @author Tadaya Tsuyukubo
 */
public class ThriftWrapperCreationTest {

	public static class Foo {
		private Collection<String> collection;

		// for cglib to create instance
		public Foo() {
		}

		public Collection<String> getCollection() {
			return collection;
		}
	}

	@Test
	public void testProxyCreation() {
		Foo fooWithNull = new Foo();  // collection is null

		Foo fooWithEmpty = new Foo(); // with empty collection
		fooWithEmpty.collection = new ArrayList<String>();

		Foo fooWithElement = new Foo(); // with collection which has entry
		List<String> list = new ArrayList<String>();
		list.add("foo");
		fooWithElement.collection = list;

		Foo result = makeNullSafe(fooWithNull);
		assertThat(AopUtils.isAopProxy(result), is(true)); // create proxy with empty field

		result = makeNullSafe(fooWithEmpty);
		assertThat(AopUtils.isAopProxy(result), is(false));

		result = makeNullSafe(fooWithElement);
		assertThat(AopUtils.isAopProxy(result), is(false));
	}
}
