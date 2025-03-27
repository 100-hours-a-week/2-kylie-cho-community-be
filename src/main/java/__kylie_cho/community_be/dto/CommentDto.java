package __kylie_cho.community_be.dto;

import __kylie_cho.community_be.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long postId;
    private String content;
    private String createdAt;
    private String updatedAt;
    private UserSummaryDto user;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt().toString();
        this.updatedAt = comment.getUpdatedAt().toString();
        this.user = (comment.getUser() != null) ? new UserSummaryDto(comment.getUser()) : null;
    }
}
