package jungle.board.service;

import jungle.board.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jungle.board.dto.PostRequestDto;
import jungle.board.dto.PostResponseDto;
import jungle.board.entity.Post;
import jungle.board.repository.PostRepository;

import java.util.List;

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
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Post getPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        return post;
    }

    @Transactional
    public Long update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        post.update(requestDto);
        return post.getId();
    }

    @Transactional
    public Long deletePost(Long id) {
        postRepository.deleteById(id);
        return id;
    }
}
