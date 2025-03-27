package __kylie_cho.community_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequestDto {
    private Long postId;
    private Long userId;
    private String content;
}
