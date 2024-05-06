package son.kingofsettlement.common.exception;

import son.kingofsettlement.common.error.StatusCodeInterface;

public interface ApiExceptionInterface {
	StatusCodeInterface getStatusCodeInterface();

	String getErrorDescription();
}
