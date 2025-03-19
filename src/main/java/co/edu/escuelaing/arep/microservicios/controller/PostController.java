package co.edu.escuelaing.arep.microservicios.controller;

import co.edu.escuelaing.arep.microservicios.model.Post;
import co.edu.escuelaing.arep.microservicios.service.JwtUtil;
import co.edu.escuelaing.arep.microservicios.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = {
        "http://frontend-mini-twitter.s3-website-us-east-1.amazonaws.com",
        "https://e124-181-237-183-246.ngrok-free.app",
        "http://127.0.0.1:5500"
}, allowedHeaders = "*", allowCredentials = "true")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Map<String, Object> requestBody) {
        String content = (String) requestBody.get("content");
        Long userId = ((Number) requestBody.get("userId")).longValue();

        Post createdPost = postService.createPost(content, userId);

        String username = createdPost.getUser().getUsername();
        String token = jwtUtil.generateToken(username);

        return ResponseEntity.ok(Map.of("post", createdPost, "token", token));
    }
}
