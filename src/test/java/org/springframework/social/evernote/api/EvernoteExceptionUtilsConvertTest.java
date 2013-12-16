package org.springframework.social.evernote.api;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

/**
 * @author Tadaya Tsuyukubo
 */
@RunWith(Parameterized.class)
public class EvernoteExceptionUtilsConvertTest {

	private Exception exception;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{new EDAMUserException()},
				{new EDAMSystemException()},
				{new EDAMNotFoundException()},
				{new RuntimeException()},
		});
	}

	public EvernoteExceptionUtilsConvertTest(Exception exception) {
		this.exception = exception;
	}

	@Test
	public void testConvert() {
		EvernoteException result = EvernoteExceptionUtils.convert(exception);
		assertThat(result.getCause(), is(instanceOf(Exception.class)));
		assertThat((Exception) result.getCause(), is(sameInstance(exception)));
	}
}
