package son.kingofsettlement.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import son.kingofsettlement.common.error.StatusCodeInterface;

@Getter
@ToString
@AllArgsConstructor
public class Result {
    private static final String FAIL = "실패";
    private static final String SUCCESS = "성공";
    private Integer code;
    private String status;
    private String message;

    public static Result success(StatusCodeInterface statusCodeInterface) {
        return new Result(statusCodeInterface.getStatusCode(), SUCCESS, statusCodeInterface.getDescription());
    }

    public static Result success(StatusCodeInterface statusCodeInterface, String message) {
        return new Result(statusCodeInterface.getStatusCode(), SUCCESS, message);
    }

    public static Result fail(StatusCodeInterface statusCodeInterface, Throwable tx) {
        return new Result(statusCodeInterface.getStatusCode(), FAIL, tx.getLocalizedMessage());
    }

    public static Result fail(StatusCodeInterface statusCodeInterface) {
        return new Result(statusCodeInterface.getStatusCode(), FAIL, statusCodeInterface.getDescription());
    }

    public static Result fail(StatusCodeInterface statusCodeInterface, String message) {
        return new Result(statusCodeInterface.getStatusCode(), FAIL, message);
    }
}
