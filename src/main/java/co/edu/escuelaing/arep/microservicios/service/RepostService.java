package co.edu.escuelaing.arep.microservicios.service;

import co.edu.escuelaing.arep.microservicios.model.Repost;
import co.edu.escuelaing.arep.microservicios.repository.RepostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepostService {
    @Autowired
    private RepostRepository repostRepository;

    public List<Repost> getAllReposts() {
        return repostRepository.findAll();
    }

    public Repost createRepost(Repost repost) {
        return repostRepository.save(repost);
    }
}
