package jungle.board.entity;

import jakarta.persistence.*;
import jungle.board.dto.PostRequestDto;
import jungle.board.dto.PostResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String postPassword;

    @Column(nullable = false)
    private String contents;

    public Post(String title, String author, String postPassword, String contents) {
        this.title = title;
        this.author = author;
        this.postPassword = postPassword;
        this.contents = contents;
    }

    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.postPassword = requestDto.getPostPassword();
        this.contents = requestDto.getContents();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.author = postRequestDto.getAuthor();
        this.postPassword = postRequestDto.getPostPassword();
        this.contents = postRequestDto.getContents();
    }
}