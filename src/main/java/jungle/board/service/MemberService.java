package jungle.board.service;

import jakarta.servlet.http.HttpServletResponse;
import jungle.board.common.jwt.JwtUtil;
import jungle.board.dto.LoginRequestDto;
import jungle.board.dto.SignupRequestDto;
import jungle.board.entity.Member;
import jungle.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public Boolean create(SignupRequestDto signupRequestDto) {
        try{
            if (memberRepository.findByUsername(signupRequestDto.getUsername()).isPresent()) {
                return false;
            }
            signupRequestDto.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
            Member member = new Member(signupRequestDto);
            memberRepository.save(member);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(jwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername()));
    }
}
