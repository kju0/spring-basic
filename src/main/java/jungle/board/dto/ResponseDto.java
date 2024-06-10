package jungle.board.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<D>{
    private final HttpStatus statusCode;
    private final String message;
    private final D data;

    public ResponseDto(HttpStatus statusCode, String message, D data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static <D> ResponseDto<D> ofSuccess(String message, D data) {
        return new ResponseDto<>(HttpStatus.OK, message, data);
    }

    public static <D> ResponseDto<D> ofFail(String message, D data) {
        return new ResponseDto<>(HttpStatus.BAD_REQUEST, message, data);
    }

    public static <D> ResponseDto<D> ofSuccess(String message) {
        return new ResponseDto<>(HttpStatus.OK, message, null);
    }

    public static <D> ResponseDto<D> ofFail(String message) {
        return new ResponseDto<>(HttpStatus.BAD_REQUEST, message, null);
    }
}