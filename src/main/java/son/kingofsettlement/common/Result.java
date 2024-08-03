package son.kingofsettlement.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import son.kingofsettlement.common.statusCode.StatusCodeInterface;

@Getter
@ToString
@AllArgsConstructor
public class Result {
	private static final String FAIL = "FAIL";
	private static final String SUCCESS = "SUCCESS";
	private Integer code;
	private String status;
	private String message;

	public static Result success(StatusCodeInterface statusCodeInterface) {
		return new Result(statusCodeInterface.getStatusCode(), SUCCESS, statusCodeInterface.getDescription());
	}

	public static Result fail(StatusCodeInterface statusCodeInterface, Throwable tx) {
		return new Result(statusCodeInterface.getStatusCode(), FAIL, tx.getLocalizedMessage());
	}

	public static Result fail(StatusCodeInterface statusCodeInterface, String message) {
		return new Result(statusCodeInterface.getStatusCode(), FAIL, message);
	}
}
