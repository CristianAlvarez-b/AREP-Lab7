package co.edu.escuelaing.arep.microservicios.service;

import co.edu.escuelaing.arep.microservicios.model.Post;
import co.edu.escuelaing.arep.microservicios.model.Repost;
import co.edu.escuelaing.arep.microservicios.model.User;
import co.edu.escuelaing.arep.microservicios.repository.PostRepository;
import co.edu.escuelaing.arep.microservicios.repository.RepostRepository;
import co.edu.escuelaing.arep.microservicios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(String content, Long userId) {
        if (content.length() > 140) {
            throw new IllegalArgumentException("El contenido excede los 140 caracteres.");
        }
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }

        Post post = new Post();
        post.setContent(content);
        post.setUser(userOpt.get());
        return postRepository.save(post);
    }
}

