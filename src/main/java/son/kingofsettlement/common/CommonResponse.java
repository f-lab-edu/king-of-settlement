package son.kingofsettlement.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.ToString;
import son.kingofsettlement.common.statusCode.StatusCodeInterface;

@Getter
@ToString
public class CommonResponse<T> {

    private Result result;

    @Valid
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T body;

    public static <T> CommonResponse<T> success(T data, Result result) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.result = result;
        commonResponse.body = data;
        return commonResponse;
    }

    public static <T> CommonResponse<T> success(T data, StatusCodeInterface statusCodeInterface) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.result = Result.success(statusCodeInterface);
        commonResponse.body = data;
        return commonResponse;
    }

    public static CommonResponse<Object> success(StatusCodeInterface statusCodeInterface) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        commonResponse.result = Result.success(statusCodeInterface);
        return commonResponse;
    }

    public static CommonResponse<Object> success(StatusCodeInterface statusCodeInterface, String message) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        commonResponse.result = Result.success(statusCodeInterface, message);
        return commonResponse;
    }

    public static <T> CommonResponse<T> fail(T data, StatusCodeInterface statusCodeInterface) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.result = Result.fail(statusCodeInterface);
        return commonResponse;
    }

    public static CommonResponse<Object> fail(StatusCodeInterface statusCodeInterface) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        commonResponse.result = Result.fail(statusCodeInterface);
        return commonResponse;
    }

    public static CommonResponse<Object> fail(StatusCodeInterface statusCodeInterface, Throwable tx) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        commonResponse.result = Result.fail(statusCodeInterface, tx);
        return commonResponse;
    }

    public static CommonResponse<Object> fail(StatusCodeInterface statusCodeInterface, String message) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        commonResponse.result = Result.fail(statusCodeInterface, message);
        return commonResponse;
    }
}
