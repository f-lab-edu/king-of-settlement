package son.kingofsettlement.common.exception;

import lombok.Getter;
import son.kingofsettlement.common.statusCode.StatusCodeInterface;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionInterface {

	private final StatusCodeInterface statusCodeInterface;
	private final String errorDescription;

	public ApiException(StatusCodeInterface statusCodeInterface) {
		super(statusCodeInterface.getDescription());
		this.statusCodeInterface = statusCodeInterface;
		this.errorDescription = statusCodeInterface.getDescription();
	}

	public ApiException(StatusCodeInterface statusCodeInterface, String errorDescription) {
		super(errorDescription);
		this.statusCodeInterface = statusCodeInterface;
		this.errorDescription = errorDescription;
	}

	public ApiException(StatusCodeInterface statusCodeInterface, Throwable tx) {
		super(tx);
		this.statusCodeInterface = statusCodeInterface;
		this.errorDescription = statusCodeInterface.getDescription();
	}

	public ApiException(StatusCodeInterface statusCodeInterface, Throwable tx, String errorDescription) {
		super(tx);
		this.statusCodeInterface = statusCodeInterface;
		this.errorDescription = errorDescription;
	}
}
