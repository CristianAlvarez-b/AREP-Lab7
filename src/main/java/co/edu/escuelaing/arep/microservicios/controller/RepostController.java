package co.edu.escuelaing.arep.microservicios.controller;

import co.edu.escuelaing.arep.microservicios.model.Post;
import co.edu.escuelaing.arep.microservicios.model.Repost;
import co.edu.escuelaing.arep.microservicios.service.RepostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/streams")
@CrossOrigin(origins = {"http://frontend-mini-twitter.s3-website-us-east-1.amazonaws.com", "https://e124-181-237-183-246.ngrok-free.app", "http://127.0.0.1:5500"}, allowedHeaders = "*", allowCredentials = "true")
public class RepostController {

    @Autowired
    private RepostService repostService;

    @GetMapping
    public List<Repost> getAllReposts() {
        return repostService.getAllReposts();
    }

    @PostMapping
    public Repost createRepost(@RequestBody Repost repost) {
        return repostService.createRepost(repost);
    }
}
