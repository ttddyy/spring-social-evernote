package org.springframework.social.evernote.api;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

/**
 * @author Tadaya Tsuyukubo
 */
@RunWith(Parameterized.class)
public class EvernoteExceptionTypeCheckTest {

	private Throwable exception;
	private boolean isEDAMUserException;
	private boolean isEDAMSystemException;
	private boolean isEDAMNotFoundException;
	private boolean isTException;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				// exception, isEDAMUserException, isEDAMSystemException, isEDAMNotFoundException, isTException
				{new EDAMUserException(), true, false, false, false},
				{new EDAMSystemException(), false, true, false, false},
				{new EDAMNotFoundException(), false, false, true, false},
				{new TException(), false, false, false, true},
				{new RuntimeException(), false, false, false, false},
		});
	}

	public EvernoteExceptionTypeCheckTest(Throwable exception, boolean EDAMUserException, boolean EDAMSystemException, boolean EDAMNotFoundException, boolean isTException) {
		this.exception = exception;
		this.isEDAMUserException = EDAMUserException;
		this.isEDAMSystemException = EDAMSystemException;
		this.isEDAMNotFoundException = EDAMNotFoundException;
		this.isTException = isTException;
	}

	@Test
	public void testExceptionType() {
		EvernoteException ex = new EvernoteException("", exception);
		assertThat(ex.isEDAMUserException(), is(isEDAMUserException));
		assertThat(ex.isEDAMSystemException(), is(isEDAMSystemException));
		assertThat(ex.isEDAMNotFoundException(), is(isEDAMNotFoundException));
		assertThat(ex.isTException(), is(isTException));
		assertThat(ex.getCause(), is(exception));
	}
}
