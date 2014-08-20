package org.springframework.social.evernote.api;

import com.evernote.edam.error.EDAMErrorCode;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteExceptionUtils {

	/**
	 * convert to {@link EvernoteException}
	 *
	 * @param ex original exception
	 * @return converted exception
	 */
	public static EvernoteException convert(Exception ex) {
		// construct exception message
		final StringBuilder sb = new StringBuilder();
		sb.append(ex.getClass().getName());
		if (ex instanceof EDAMUserException) {
			final EDAMErrorCode errorCode = ((EDAMUserException) ex).getErrorCode();
			if (errorCode == null) {
				sb.append(", ErrorCode [null");
			} else {
				sb.append(", ErrorCode [" + errorCode + "(" + errorCode.getValue() + ")");
			}
			sb.append("], Parameter [");
			sb.append(((EDAMUserException) ex).getParameter());
			sb.append("]");
		} else if (ex instanceof EDAMSystemException) {
			final EDAMErrorCode errorCode = ((EDAMSystemException) ex).getErrorCode();
			if (errorCode == null) {
				sb.append(", ErrorCode [null");
			} else {
				sb.append(", ErrorCode [" + errorCode + "(" + errorCode.getValue() + ")");
			}
			sb.append("], Message [");
			sb.append(((EDAMSystemException) ex).getMessage());
			sb.append("], RateLimitDuration [");
			sb.append(((EDAMSystemException) ex).getRateLimitDuration());
			sb.append("]");
		} else if (ex instanceof EDAMNotFoundException) {
			sb.append(", Identifier [");
			sb.append(((EDAMNotFoundException) ex).getIdentifier());
			sb.append("], Key [");
			sb.append(((EDAMNotFoundException) ex).getKey());
			sb.append("]");
		} else {
			sb.append(", Message [");
			sb.append(ex.getMessage());
			sb.append("]");
		}
		return new EvernoteException(sb.toString(), ex);
	}

	/**
	 * Execute the given operation and wrap the thrown exception to {@link EvernoteException}.
	 *
	 * @param operation a callback operation
	 * @param <T>       operation return type
	 * @return return from callback operation
	 * @throws EvernoteException EvernoteException wrapping a thrown original exception
	 */
	public static <T> T wrap(Operation<T> operation) throws EvernoteException {
		try {
			return operation.execute();
		} catch (Exception ex) {
			throw convert(ex);
		}
	}

	/**
	 * Callback interface for {@link EvernoteExceptionUtils#wrap(org.springframework.social.evernote.api.EvernoteExceptionUtils.Operation)}.
	 *
	 * @param <T>
	 */
	public static interface Operation<T> {
		T execute() throws Exception;
	}
}
