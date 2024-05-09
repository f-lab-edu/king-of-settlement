package son.kingofsettlement.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.EncryptException;
import son.kingofsettlement.user.exception.SignUpException;
import son.kingofsettlement.user.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


// 해당 클래스가 비즈니스 로직을 수행하는 서비스 클래스임을 나타내는 어노테이션으로, 주로 서비스 계층의 클래스에 사용
@Service
// Lombok 라이브러리에서 제공하는 어노테이션으로, final 필드가 있는 생성자를 생성해주는 역할
@RequiredArgsConstructor
// 트랜잭션 처리를 지원하기 위한 어노테이션으로, 해당 메소드나 클래스에 트랜잭션을 적용
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User signUp(SignUpRequest req) throws EncryptException, SignUpException {
        isDuplicatedUser(req.getEmail());
        String encryptedEmail = AESEncryption.encrypt(req.getEmail());
        byte[] salt = generateSalt();
        String encryptedPassword = hashPassword(req.getPassword(), salt);
        User user = User.inActiveStatusOf(encryptedEmail, encryptedPassword);
        return userRepository.save(user);
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(salt);
            StringBuilder encryptPassword = new StringBuilder();
            byte[] hashedByteArrayPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            for (byte b : hashedByteArrayPassword) {
                encryptPassword.append(String.format("%02x", b));
            }
            return String.valueOf(encryptPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e.getMessage());
        }
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[256];
        random.nextBytes(salt);
        return salt;
    }

    public void isDuplicatedUser(String email) throws SignUpException {
        if (userRepository.findOneByEmail(email).isPresent()) {
            throw new SignUpException("중복된 이메일입니다.");
        }
    }
}
