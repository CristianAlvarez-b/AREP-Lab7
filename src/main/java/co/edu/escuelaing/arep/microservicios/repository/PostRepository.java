package co.edu.escuelaing.arep.microservicios.repository;

import co.edu.escuelaing.arep.microservicios.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {}