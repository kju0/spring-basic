package jungle.board.controller;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ResponseDto<PostResponseDto>> createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        try{
            PostResponseDto postResponseDto = postService.createPost(requestDto, request);
            ResponseDto<PostResponseDto> responseDto = ResponseDto.ofSuccess("Post created successfully", postResponseDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ResponseDto<PostResponseDto> responseDto = ResponseDto.ofFail(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ResponseDto<PostResponseDto> responseDto = ResponseDto.ofFail("Failed to create post");
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseDto<List<PostResponseDto>>> getPosts() {
        try {
            ResponseDto<List<PostResponseDto>> responseDto =  ResponseDto.ofSuccess("Post list good", postService.getPosts());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch(Exception e) {
            ResponseDto<List<PostResponseDto>> responseDto =  ResponseDto.ofFail("Post list failed", postService.getPosts());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ResponseDto<PostResponseDto>> getPostDetail(@PathVariable Long id) {
        try{
            PostResponseDto postResponseDto = postService.getPostDetail(id);
            ResponseDto<PostResponseDto> responseDto = ResponseDto.ofSuccess("success", postResponseDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDto<PostResponseDto> responseDto = ResponseDto.ofFail("fail");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<ResponseDto<Long>> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        if (postService.update(id, requestDto, request)){
            ResponseDto<Long> responseDto = ResponseDto.ofSuccess("Post updated successfully");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

        ResponseDto<Long> responseDto = ResponseDto.ofFail("Failed to update post");
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ResponseDto<Long>> deletePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        if (postService.deletePost(id, requestDto, request)){
            ResponseDto<Long> responseDto = ResponseDto.ofSuccess("Post deleted successfully");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        ResponseDto<Long> responseDto = ResponseDto.ofSuccess("Failed to delete post");
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
