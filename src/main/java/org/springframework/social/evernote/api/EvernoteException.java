package org.springframework.social.evernote.api;

import com.evernote.edam.error.EDAMErrorCode;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;

/**
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

	public EDAMErrorCode getEDAMErrorCode() {
		if (isEDAMUserException()) {
			return ((EDAMUserException) getCause()).getErrorCode();
		} else if (isEDAMSystemException()) {
			return ((EDAMSystemException) getCause()).getErrorCode();
		}
		return null;
	}
}
