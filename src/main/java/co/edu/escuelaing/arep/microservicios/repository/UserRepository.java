package co.edu.escuelaing.arep.microservicios.repository;

import co.edu.escuelaing.arep.microservicios.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
