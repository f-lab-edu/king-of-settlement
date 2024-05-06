package son.kingofsettlement.common.exception;

import lombok.Getter;
import son.kingofsettlement.common.statusCode.StatusCodeInterface;

@Getter
public class UserException extends RuntimeException implements ApiExceptionInterface {

	private final StatusCodeInterface statusCodeInterface;
	private final String errorDescription;

	public UserException(StatusCodeInterface statusCodeInterface) {
		super(statusCodeInterface.getDescription());
		this.statusCodeInterface = statusCodeInterface;
		this.errorDescription = statusCodeInterface.getDescription();
	}

	public UserException(StatusCodeInterface statusCodeInterface, String errorDescription) {
		super(errorDescription);
		this.statusCodeInterface = statusCodeInterface;
		this.errorDescription = errorDescription;
	}

	public UserException(StatusCodeInterface statusCodeInterface, Throwable tx) {
		super(tx);
		this.statusCodeInterface = statusCodeInterface;
		this.errorDescription = statusCodeInterface.getDescription();
	}

	public UserException(StatusCodeInterface statusCodeInterface, Throwable tx, String errorDescription) {
		super(tx);
		this.statusCodeInterface = statusCodeInterface;
		this.errorDescription = errorDescription;
	}
}
