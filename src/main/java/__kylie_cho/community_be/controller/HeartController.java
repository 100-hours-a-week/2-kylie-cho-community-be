package __kylie_cho.community_be.controller;

import __kylie_cho.community_be.entity.Heart;
import __kylie_cho.community_be.service.HeartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hearts")
public class HeartController {

    private final HeartService heartService;

    public HeartController(HeartService heartService) {
        this.heartService = heartService;
    }

    // 좋아요 누르기
    @PostMapping
    public ResponseEntity<Heart> likePost(@RequestParam Long userId, @RequestParam Long postId) {
        Heart heart = heartService.addHeart(userId, postId);
        return ResponseEntity.ok(heart);
    }

    // 좋아요 삭제
    @DeleteMapping
    public ResponseEntity<Void> unlikePost(@RequestParam Long userId, @RequestParam Long postId) {
        heartService.removeHeart(userId, postId);
        return ResponseEntity.noContent().build();
    }

    // 특정 게시글에 대한 좋아요 수 조회
    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> getHeartCount(@PathVariable Long postId) {
        long heartCount = heartService.countHearts(postId);
        return ResponseEntity.ok(heartCount);
    }
}
