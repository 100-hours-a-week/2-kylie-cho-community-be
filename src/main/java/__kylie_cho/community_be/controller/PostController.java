package __kylie_cho.community_be.controller;

import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> getPosts(@RequestParam(defaultValue = "0") Integer offset,
                               @RequestParam(defaultValue = "10") Integer limit) {
        return postService.getPosts(offset, limit);
    }
}
