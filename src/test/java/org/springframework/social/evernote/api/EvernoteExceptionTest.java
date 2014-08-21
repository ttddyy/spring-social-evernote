package org.springframework.social.evernote.api;

import com.evernote.edam.error.EDAMErrorCode;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteExceptionTest {

	@Test
	public void testEdamErrorCode() {
		EDAMUserException edamUserException = mock(EDAMUserException.class);
		EDAMSystemException edamSystemException = mock(EDAMSystemException.class);
		when(edamUserException.getErrorCode()).thenReturn(EDAMErrorCode.UNKNOWN);
		when(edamSystemException.getErrorCode()).thenReturn(EDAMErrorCode.UNKNOWN);

		EvernoteException ex = new EvernoteException("", edamUserException);
		assertThat(ex.getEDAMErrorCode(), is(EDAMErrorCode.UNKNOWN));

		ex = new EvernoteException("", edamSystemException);
		assertThat(ex.getEDAMErrorCode(), is(EDAMErrorCode.UNKNOWN));

		ex = new EvernoteException("", new RuntimeException());
		assertThat(ex.getEDAMErrorCode(), is(nullValue()));

	}

	@Test
	public void testIsEdamException() {
		EvernoteException ex = new EvernoteException("", new EDAMUserException());
		assertTrue(ex.isEDAMException());

		ex = new EvernoteException("", new EDAMSystemException());
		assertTrue(ex.isEDAMException());

		ex = new EvernoteException("", new EDAMNotFoundException());
		assertTrue(ex.isEDAMException());


		ex = new EvernoteException("", new TException());
		assertFalse(ex.isEDAMException());

		ex = new EvernoteException("", new RuntimeException());
		assertFalse(ex.isEDAMException());
	}
}
