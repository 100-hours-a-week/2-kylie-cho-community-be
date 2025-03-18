package __kylie_cho.community_be.controller;

import __kylie_cho.community_be.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public List<String> getPosts() {
        return postService.getPosts();
    }
}
