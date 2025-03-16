package co.edu.escuelaing.arep.microservicios.controller;


import co.edu.escuelaing.arep.microservicios.model.Post;
import co.edu.escuelaing.arep.microservicios.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public Post createPost(@RequestBody Map<String, Object> requestBody) {
        String content = (String) requestBody.get("content");
        Long userId = ((Number) requestBody.get("userId")).longValue();
        return postService.createPost(content, userId);
    }
}