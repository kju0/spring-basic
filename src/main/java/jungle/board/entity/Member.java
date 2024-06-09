package jungle.board.entity;

import jakarta.persistence.*;
import jungle.board.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member {

    //username 최소 4자 이상, 10자 이하 알파벳 소문자 숫자
    //password는 최소 8자 이상, 15자 이하, 알파벳 대소문자, 숫자
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public Member(SignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
    }
}
