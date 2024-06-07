package jungle.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
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
}

