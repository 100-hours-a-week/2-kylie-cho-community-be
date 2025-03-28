package __kylie_cho.community_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeartRequestDto {
    private Long userId;
    private Long postId;
}
