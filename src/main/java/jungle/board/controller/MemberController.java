package jungle.board.controller;

import jakarta.servlet.http.HttpServletResponse;
import jungle.board.dto.LoginRequestDto;
import jungle.board.dto.SignupRequestDto;
import jungle.board.dto.ResponseDto;
import jungle.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Object>> createMember(@Validated @RequestBody SignupRequestDto signupRequestDto) {
        if (memberService.create(signupRequestDto)) {
            ResponseDto<Object> responseDto = ResponseDto.ofSuccess("Member created successfully");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        ResponseDto<Object> responseDto = ResponseDto.ofFail("fail");
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Object>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            memberService.login(loginRequestDto, response);
            ResponseDto<Object> responseDto = ResponseDto.ofSuccess("success");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDto<Object> responseDto = ResponseDto.ofSuccess("fail");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
