package wide_commerce.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wide_commerce.rest.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findFirstByUsername(String username);
    public Optional<User> findFirstByToken(String token);
}
