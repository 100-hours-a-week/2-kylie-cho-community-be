package __kylie_cho.community_be.repository;

import __kylie_cho.community_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}
