package __kylie_cho.community_be.controller;

import __kylie_cho.community_be.dto.HeartDto;
import __kylie_cho.community_be.dto.HeartRequestDto;
import __kylie_cho.community_be.service.HeartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hearts")
public class HeartController {

    private final HeartService heartService;

    public HeartController(HeartService heartService) {
        this.heartService = heartService;
    }

    // 좋아요 누르기
    @PostMapping
    public ResponseEntity<HeartDto> likePost(@RequestBody HeartRequestDto request) {
        HeartDto heartDto = heartService.addHeart(request.getUserId(), request.getPostId());
        return ResponseEntity.ok(heartDto);
    }

    // 좋아요 삭제
    @DeleteMapping
    public ResponseEntity<Void> unlikePost(@RequestBody HeartRequestDto request) {
        heartService.removeHeart(request.getUserId(), request.getPostId());
        return ResponseEntity.noContent().build();
    }

    // 특정 게시글에 대한 좋아요 수 조회
    @GetMapping("/count")
    public ResponseEntity<Long> getHeartCount(@RequestBody HeartRequestDto request) {
        long heartCount = heartService.countHearts(request.getPostId());
        return ResponseEntity.ok(heartCount);
    }

    // 특정 게시글에 대해 사용자가 좋아요를 눌렀는지 여부 확인
    @GetMapping("/check")
    public ResponseEntity<Boolean> isHearted(@RequestParam Long userId, @RequestParam Long postId) {
        boolean isHearted = heartService.isHearted(userId, postId);
        return ResponseEntity.ok(isHearted);
    }
}
