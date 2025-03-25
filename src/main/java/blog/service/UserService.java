package blog.service;

import blog.JwtUtil;
import blog.dto.LoginRequest;
import blog.dto.UserRegisterRequest;
import blog.entity.User;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import blog.repository.UserRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 회원 가입 기능
    public void register(UserRegisterRequest request) {
        // 비밀번호 암호화
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        // 중복 체크
        if(userRepository.existsById(request.getId())) {
            throw new IllegalArgumentException("이미 존재하는 ID입니다.");
        }

        User user = new User();
        user.setId(request.getId());
        user.setPassword(hashedPassword);
        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname());
        user.setCreatedAt(Instant.now());

        userRepository.save(user);
    }

    public String login(LoginRequest request) {

        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 비밀번호 검증
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성 및 반환
        return jwtUtil.generateToken(user.getId());
    }

    public boolean existsById(String userId) {
        return userRepository.findById(userId).isPresent();
    }

    // 기타 메서드...
}
