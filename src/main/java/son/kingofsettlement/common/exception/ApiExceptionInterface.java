package son.kingofsettlement.common.exception;

import son.kingofsettlement.common.statusCode.StatusCodeInterface;

public interface ApiExceptionInterface {
	StatusCodeInterface getStatusCodeInterface();

	String getErrorDescription();
}
