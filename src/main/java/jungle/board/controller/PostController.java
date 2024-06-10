package jungle.board.controller;

import jungle.board.dto.PostRequestDto;
import jungle.board.dto.PostResponseDto;
import jungle.board.dto.ResponseDto;
import jungle.board.entity.Post;
import jungle.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/posts")
    public ResponseEntity<ResponseDto<PostResponseDto>> createPost(@RequestBody PostRequestDto requestDto) {
        try{
            PostResponseDto postResponseDto = postService.createPost(requestDto);
            ResponseDto<PostResponseDto> responseDto = ResponseDto.ofSuccess("Post created successfully", postResponseDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDto<PostResponseDto> responseDto = ResponseDto.ofFail("Failed to create post", null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() { //todo: responseEntity로 감싸줘야할까?
        return postService.getPosts();
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto getPostDetail(@PathVariable Long id) {
        return postService.getPostDetail(id);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<ResponseDto<Long>> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        if (postService.update(id, requestDto)){
            ResponseDto<Long> responseDto = ResponseDto.ofSuccess("Post updated successfully", id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

        ResponseDto<Long> responseDto = ResponseDto.ofFail("Failed to update post", id);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ResponseDto<Long>> deletePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        if (postService.deletePost(id, requestDto)){
            ResponseDto<Long> responseDto = ResponseDto.ofSuccess("Post deleted successfully", id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        ResponseDto<Long> responseDto = ResponseDto.ofSuccess("Failed to delete post", id);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
