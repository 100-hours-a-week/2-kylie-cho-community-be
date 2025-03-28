package __kylie_cho.community_be.dto;

import __kylie_cho.community_be.entity.Heart;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeartDto {
    private Long id;
    private Long postId;
    private Long userId;

    public HeartDto(Heart heart) {
        this.id = heart.getId();
        this.postId = heart.getPost().getId();
        this.userId = heart.getUser().getId();
    }
}
