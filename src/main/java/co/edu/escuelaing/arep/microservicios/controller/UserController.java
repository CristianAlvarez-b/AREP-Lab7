package co.edu.escuelaing.arep.microservicios.controller;

import co.edu.escuelaing.arep.microservicios.model.User;
import co.edu.escuelaing.arep.microservicios.service.JwtUtil;
import co.edu.escuelaing.arep.microservicios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://frontend-mini-twitter.s3-website-us-east-1.amazonaws.com", "https://e124-181-237-183-246.ngrok-free.app", "http://127.0.0.1:5500"}, allowedHeaders = "*", allowCredentials = "true")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        String token = jwtUtil.generateToken(createdUser.getUsername());
        return ResponseEntity.ok(Map.of("user", createdUser, "token", token));
    }
}

