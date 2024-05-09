package son.kingofsettlement.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.ToString;
import son.kingofsettlement.common.error.StatusCodeInterface;


@ToString
@Getter
public class CommonResponse<T> {
    /*
    {
        "result" : {},
        "body" : {}
    }
     */
    private Result result;
    /*
        @Valid:
        자바 플랫폼의 표준 스펙 중 하나인 Bean Validation(JSR-380)을 사용하여 입력 데이터의 유효성을 검사할 때 사용.
        유효성 검사를 위한 어노테이션이 포함되어 있다면, 객체의 유효성을 검사하여 유효하지 않은 경우에는 예외를 발생.
     */
    @Valid
    /*
        @JsonInclude:
        Jackson 라이브러리에서 JSON 직렬화 시에 특정 필드들을 포함하거나 제외하는 데 사용.

        JsonInclude.Include.NON_NULL:
        null 값을 가진 필드들을 JSON으로 직렬화할 때 제외시킨다.
     */
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
