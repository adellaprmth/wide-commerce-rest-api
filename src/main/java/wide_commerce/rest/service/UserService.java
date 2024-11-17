package wide_commerce.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wide_commerce.rest.dto.UserRequest;
import wide_commerce.rest.dto.UserResponse;
import wide_commerce.rest.entity.User;
import wide_commerce.rest.repository.UserRepository;
import wide_commerce.rest.security.BCrypt;
import wide_commerce.rest.utils.RandomStringGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(UserRequest request){
        Optional<User> byUsername = userRepository.findFirstByUsername(request.getUsername());
        if (byUsername.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User Already Exist.");
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt()));

        userRepository.save(user);
    }

    public UserResponse login(UserRequest request){
        User user = userRepository.findFirstByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password invalid."));

        boolean checkpw = BCrypt.checkpw(request.getPassword(), user.getPassword());
        if (!checkpw){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password invalid");
        }
        user.setToken(RandomStringGenerator.generate(15));
        user.setTokenExpiredAt(getTokenExpiredTime(24));

        userRepository.save(user);

        return UserResponse
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .token(user.getToken())
                .tokenExpiredAt(user.getTokenExpiredAt())
                .build();
    }

    private Timestamp getTokenExpiredTime(int hour){
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime localDateTime = currentTimestamp.toLocalDateTime();
        LocalDateTime updatedDateTime = localDateTime.plusHours(hour);
        return Timestamp.valueOf(updatedDateTime);
    }
}
