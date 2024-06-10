package jungle.board.service;

import jungle.board.dto.PostResponseDto;
import jungle.board.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jungle.board.dto.PostRequestDto;
import jungle.board.dto.PostResponseDto;
import jungle.board.entity.Post;
import jungle.board.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto getPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public Boolean update(Long id, PostRequestDto requestDto) {
        if (checkPassword(id, requestDto)) {
            Post post = postRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );
            post.update(requestDto);
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean deletePost(Long id, PostRequestDto requestDto) {
        if (checkPassword(id, requestDto)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Boolean checkPassword(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        return post.getPostPassword().equals(requestDto.getPostPassword());
    }
}
