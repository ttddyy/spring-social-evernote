package org.springframework.social.evernote.api.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.thrift.protocol.TProtocol;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Tadaya Tsuyukubo
 */
public class ThriftNullSafeCollectionInterceptorTest {

	public class Foo {
		public List<String> list = new ArrayList<String>();
		public Set<String> set = new HashSet<String>();
		public Map<String, String> map = new HashMap<String, String>();


		public void write(TProtocol tprotocol) {
			// simulate Thrift write method
		}
	}

	@Test
	public void testInvocation() throws Throwable {

		Foo foo = new Foo();
		Method writeMethod = ReflectionUtils.findMethod(Foo.class, "write", TProtocol.class);

		MethodInvocation invocation = mock(MethodInvocation.class);
		when(invocation.getMethod()).thenReturn(writeMethod);
		when(invocation.getThis()).thenReturn(foo);

		Field listField = ReflectionUtils.findField(Foo.class, "list");
		Field listSet = ReflectionUtils.findField(Foo.class, "set");
		Field listMap = ReflectionUtils.findField(Foo.class, "map");

		ThriftNullSafeCollectionInterceptor interceptor = new ThriftNullSafeCollectionInterceptor();
		interceptor.getInitiallyNullListFields().add(listField);
		interceptor.getInitiallyNullSetFields().add(listSet);
		interceptor.getInitiallyNullMapFields().add(listMap);

		interceptor.invoke(invocation);

		assertThat(foo.list, is(nullValue()));
		assertThat(foo.set, is(nullValue()));
		assertThat(foo.map, is(nullValue()));
	}
}
