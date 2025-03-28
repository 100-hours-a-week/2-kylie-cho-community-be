package __kylie_cho.community_be.dto;

import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.entity.User;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String image;
    private int heartCount;
    private int viewCount;
    private int commentCount;
    private boolean isHearted;
    private String createdAt;
    private String updatedAt;
    private UserSummaryDto user;

    // 게시글 상세 조회용 생성자
    public PostResponseDto(Post post, long viewCount, long commentCount, long heartCount, boolean isHearted) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.image = post.getImage();
        this.heartCount = (int) heartCount;
        this.viewCount = (int) viewCount;
        this.commentCount = (int) commentCount;
        this.isHearted = isHearted;
        this.createdAt = post.getCreatedAt().toString();
        this.updatedAt = post.getUpdatedAt().toString();
        this.user = (post.getUser() != null) ? new UserSummaryDto(post.getUser()) : null;
    }

    // 게시글 목록 조회용 생성자
    public PostResponseDto(Post post, long viewCount, long commentCount, long heartCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.image = post.getImage();
        this.heartCount = (int) heartCount;
        this.viewCount = (int) viewCount;
        this.commentCount = (int) commentCount;
        this.isHearted = false; // 기본값 설정
        this.createdAt = post.getCreatedAt().toString();
        this.updatedAt = post.getUpdatedAt().toString();
        this.user = (post.getUser() != null) ? new UserSummaryDto(post.getUser()) : null;
    }
}
