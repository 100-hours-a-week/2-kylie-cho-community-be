package __kylie_cho.community_be.dto;

import __kylie_cho.community_be.entity.User;
import lombok.Getter;

@Getter
public class UserSummaryDto {
    private Long id;
    private String nickname;
    private String profileImage;

    public UserSummaryDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
    }
}
