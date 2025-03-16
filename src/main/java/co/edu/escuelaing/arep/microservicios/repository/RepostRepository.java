package co.edu.escuelaing.arep.microservicios.repository;

import co.edu.escuelaing.arep.microservicios.model.Repost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepostRepository extends JpaRepository<Repost, Long> {}