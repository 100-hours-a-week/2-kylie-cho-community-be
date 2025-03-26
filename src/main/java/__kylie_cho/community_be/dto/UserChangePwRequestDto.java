package __kylie_cho.community_be.dto;

import lombok.Getter;

@Getter
public class UserChangePwRequestDto {
    private String oldPassword;
    private String newPassword;
}
