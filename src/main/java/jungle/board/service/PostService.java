package jungle.board.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jungle.board.common.jwt.JwtUtil;
import jungle.board.dto.PostResponseDto;
import jungle.board.dto.ResponseDto;
import jungle.board.entity.Member;
import jungle.board.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    private String checkToken(HttpServletRequest request) {
        //token 확인
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        String username = null;

        if (token == null) {
            throw new IllegalArgumentException("Token Error");
        }

        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
            Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            username = claims.getSubject();
        } else {
            throw new IllegalArgumentException("Token Error");
        }

        return username;
    }

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        String username = checkToken(request);

        Post post = new Post(requestDto, username);
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
    public Boolean update(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        Post post = checkPassword(id, requestDto, request);
        if (post != null) {
            post.update(requestDto);
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean deletePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        if (checkPassword(id, requestDto, request) != null) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Post checkPassword(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        String username = checkToken(request);

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        if (post.getAuthor().equals(username))
            return post;
        return null;
    }
}
