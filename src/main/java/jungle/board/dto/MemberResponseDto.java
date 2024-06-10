package jungle.board.dto;

import jungle.board.entity.Member;
import jungle.board.entity.Post;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private String username;

    public MemberResponseDto(Member member) {
        this.username = getUsername();
    }
}