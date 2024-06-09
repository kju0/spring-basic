package jungle.board.dto;

import lombok.Getter;

@Getter
public class RequestDto<D> {
    private final Long id;
    private final D data;

    public RequestDto(Long id, D data) {
        this.id = id;
        this.data = data;
    }

}
