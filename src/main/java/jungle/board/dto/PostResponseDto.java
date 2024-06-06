package jungle.board.dto;

import jungle.board.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private String title;
    private String author;
    private String contents;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.contents = post.getContents();
    }
}