package co.edu.escuelaing.arep.microservicios.service;

import co.edu.escuelaing.arep.microservicios.model.User;
import co.edu.escuelaing.arep.microservicios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User createUser( User user) {
        return userRepository.save(user);
    }
}
