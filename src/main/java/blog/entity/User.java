package blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@RequiredArgsConstructor
public class User {
    private String id;
    private String password;
    private String email;
    private String nickname;
    @JsonIgnore
    private Instant createdAt;
}
