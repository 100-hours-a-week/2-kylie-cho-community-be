package __kylie_cho.community_be.repository;

import __kylie_cho.community_be.entity.Comment;
import __kylie_cho.community_be.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    // 댓글수 조회
    long countByPostId(Long postId);
}
