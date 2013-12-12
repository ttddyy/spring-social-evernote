package org.springframework.social.evernote.api;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;

/**
 * @author Tadaya Tsuyukubo
 */
public class EvernoteExceptionUtils {

	public static EvernoteException convert(Exception ex) {
		StringBuilder sb = new StringBuilder();
		sb.append(ex.getMessage());
		if (ex instanceof EDAMUserException) {
			sb.append(", ErrorCode [");
			sb.append(((EDAMUserException) ex).getErrorCode());
			sb.append("], Parameter [");
			sb.append(((EDAMUserException) ex).getParameter());
			sb.append("]");
		}
		if (ex instanceof EDAMSystemException) {
			sb.append(", ErrorCode [");
			sb.append(((EDAMSystemException) ex).getErrorCode());
			sb.append("], RateLimitDuration [");
			sb.append(((EDAMSystemException) ex).getRateLimitDuration());
			sb.append("]");
		}
		if (ex instanceof EDAMNotFoundException) {
			sb.append(", Identifier [");
			sb.append(((EDAMNotFoundException) ex).getIdentifier());
			sb.append("], Key [");
			sb.append(((EDAMNotFoundException) ex).getKey());
			sb.append("]");
		}
		return new EvernoteException(sb.toString(), ex);
	}

	public static <T> T wrap(Operation<T> operation) throws EvernoteException {
		try {
			return operation.execute();
		} catch (Exception ex) {
			throw convert(ex);
		}
	}

	public static interface Operation<T> {
		T execute() throws Exception;
	}
}
