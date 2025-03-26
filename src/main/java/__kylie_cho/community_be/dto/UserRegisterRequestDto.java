package __kylie_cho.community_be.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserRegisterRequestDto {
    private String email;
    private String nickname;
    private String password;
    private MultipartFile profileImage;
}
