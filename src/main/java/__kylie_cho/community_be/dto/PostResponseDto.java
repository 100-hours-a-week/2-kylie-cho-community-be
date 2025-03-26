package __kylie_cho.community_be.dto;

import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.entity.User;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private int heartCount;
    private int viewCount;
    private int commentCount;
    private String createdAt;
    private String updatedAt;
    private String image;
    private UserSummaryDto user;

    public PostResponseDto(Post post, long viewCount, long commentCount, long heartCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.heartCount = (int) heartCount;
        this.viewCount = (int) viewCount;
        this.commentCount = (int) commentCount;
        this.createdAt = post.getCreatedAt().toString();
        this.updatedAt = post.getUpdatedAt().toString();
        this.user = (post.getUser() != null) ? new UserSummaryDto(post.getUser()) : null;
    }
}
