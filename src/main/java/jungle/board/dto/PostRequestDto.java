package jungle.board.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String author;
    private String postPassword;
    private String contents;
}