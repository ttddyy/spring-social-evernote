package org.springframework.social.evernote.api;

import com.evernote.edam.error.EDAMErrorCode;
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
public class EvernoteExceptionUtilsConvertGetMessageTest {

	private Exception exception;
	private String expectedMessage;

	@Parameters
	public static Collection<Object[]> data() {
		EDAMUserException edamUserException = new EDAMUserException();
		edamUserException.setErrorCode(EDAMErrorCode.AUTH_EXPIRED);
		edamUserException.setParameter("PARAM");

		EDAMSystemException edamSystemException = new EDAMSystemException();
		edamSystemException.setErrorCode(EDAMErrorCode.BAD_DATA_FORMAT);
		edamSystemException.setMessage("MESSAGE");
		edamSystemException.setRateLimitDuration(99);

		EDAMNotFoundException edamNotFoundException = new EDAMNotFoundException();
		edamNotFoundException.setIdentifier("IDENTIFIER");
		edamNotFoundException.setKey("KEY");

		TException tException = new TException("TEXCEPTION_MESSAGE");

		RuntimeException runtimeException = new RuntimeException("RUNTIME_EXCEPTION_MESSAGE");

		return Arrays.asList(new Object[][]{
				{edamUserException, "com.evernote.edam.error.EDAMUserException, ErrorCode [AUTH_EXPIRED], Parameter [PARAM]"},
				{edamSystemException, "com.evernote.edam.error.EDAMSystemException, ErrorCode [BAD_DATA_FORMAT], Message [MESSAGE], RateLimitDuration [99]"},
				{edamNotFoundException, "com.evernote.edam.error.EDAMNotFoundException, Identifier [IDENTIFIER], Key [KEY]"},
				{tException, "com.evernote.thrift.TException, Message [TEXCEPTION_MESSAGE]"},
				{runtimeException, "java.lang.RuntimeException, Message [RUNTIME_EXCEPTION_MESSAGE]"},
		});
	}

	public EvernoteExceptionUtilsConvertGetMessageTest(Exception exception, String expectedMessage) {
		this.exception = exception;
		this.expectedMessage = expectedMessage;
	}

	@Test
	public void testGetMessage() {
		String result = EvernoteExceptionUtils.convert(exception).getMessage();
		assertThat(result, is(expectedMessage));
	}
}
