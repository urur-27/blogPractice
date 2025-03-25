package blog.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import blog.entity.User;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final String FILE_PATH = "output/users.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 모든 사용자 불러오기
    public List<User> load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try {
            return Arrays.asList(objectMapper.readValue(file, User[].class));
        } catch (IOException e) {
            throw new RuntimeException("사용자 파일 읽기 실패", e);
        }
    }

    // 사용자 추가 후 저장
    public void save(User newUser) {
        List<User> users = load();
        users.add(newUser);
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            objectMapper.writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            throw new RuntimeException("사용자 파일 저장 실패", e);
        }
    }

    // ID 중복 검사
    public boolean existsById(String id) {
        return load()
                .stream()
                .anyMatch(user -> user.getId().equals(id));
    }

    public Optional<User> findById(String id) {
        return load()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
}
