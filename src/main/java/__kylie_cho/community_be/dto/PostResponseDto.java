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
    private AuthorDto author;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.heartCount = post.getHearts().size();
        this.viewCount = post.getViewCount();
        this.commentCount = post.getComments().size();
        this.createdAt = post.getCreatedAt().toString();
        this.updatedAt = post.getUpdatedAt().toString();
        this.image = post.getImage();
        this.author = new AuthorDto(post.getUser());
    }

    @Getter
    public static class AuthorDto {
        private String nickname;
        private String profileImage;

        public AuthorDto(User user) {
            this.nickname = user.getNickname();
            this.profileImage = user.getProfileImage();
        }
    }

}
