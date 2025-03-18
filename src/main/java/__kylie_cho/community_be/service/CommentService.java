package __kylie_cho.community_be.service;

import __kylie_cho.community_be.entity.Comment;
import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.entity.User;
import __kylie_cho.community_be.repository.CommentRepository;
import __kylie_cho.community_be.repository.PostRepository;
import __kylie_cho.community_be.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 특정 게시글에 대한 댓글 조회
    public List<Comment> getCommentsByPost(Long id) {
        return commentRepository.findByPostId(id);
    }

    // 댓글 작성
    @Transactional
    public Comment createComment(Long postId, Long userId, String content) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);

        return commentRepository.save(comment);
    }

    // 댓글 수정
    @Transactional
    public void updateComment(Long id, String newContent) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.setContent(newContent);
        commentRepository.save(comment);
    }

}
