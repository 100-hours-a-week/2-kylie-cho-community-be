package __kylie_cho.community_be.dto;

import lombok.Getter;

@Getter
public class UserChangePwRequest {
    private String oldPassword;
    private String newPassword;
}
