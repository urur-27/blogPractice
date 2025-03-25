package blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserRegisterRequest {

    @NotBlank
    @Size(min  = 6, max = 30)
    private String id;

    @NotBlank
    @Size(min = 12, max = 50)
    @Pattern(
            regexp = "^(?=(.*[a-zA-Z]){2,})(?=(.*\\d){2,})(?=(.*[!@#$%^&*]){2,}).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자를 각각 2개 이상 포함해야 합니다."
    )
    private String password;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    private String nickname;
}
