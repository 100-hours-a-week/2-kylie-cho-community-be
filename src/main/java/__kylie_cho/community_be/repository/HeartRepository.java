package __kylie_cho.community_be.repository;

import __kylie_cho.community_be.entity.Heart;
import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

//    boolean existsByUserIdAndPostId(Long userId, Long postId);

    Optional<Heart> findByUserAndPost(User user, Post post);

    long countByPost(Post post);
}
