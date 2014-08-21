package org.springframework.social.evernote.api;

import com.evernote.edam.error.EDAMErrorCode;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;

/**
 * Runtime exception that encapsulates typed exceptions from evernote sdk.
 *
 * @author Tadaya Tsuyukubo
 */
public class EvernoteException extends RuntimeException {

	public EvernoteException(String message, Throwable cause) {
		super(message, cause);
	}

	public boolean isEDAMUserException() {
		return getCause() instanceof EDAMUserException;
	}

	public boolean isEDAMSystemException() {
		return getCause() instanceof EDAMSystemException;
	}

	public boolean isEDAMNotFoundException() {
		return getCause() instanceof EDAMNotFoundException;
	}

	public boolean isTException() {
		return getCause() instanceof TException;
	}

	/**
	 * True if exception is one of {@link EDAMUserException}, {@link EDAMSystemException}, or {@link EDAMNotFoundException}
	 *
	 * @return true when thrown exception is one of EDAM*Exception.
	 */
	public boolean isEDAMException() {
		return isEDAMUserException() || isEDAMSystemException() || isEDAMNotFoundException();
	}

	public EDAMErrorCode getEDAMErrorCode() {
		if (isEDAMUserException()) {
			return ((EDAMUserException) getCause()).getErrorCode();
		} else if (isEDAMSystemException()) {
			return ((EDAMSystemException) getCause()).getErrorCode();
		}
		return null;
	}
}
