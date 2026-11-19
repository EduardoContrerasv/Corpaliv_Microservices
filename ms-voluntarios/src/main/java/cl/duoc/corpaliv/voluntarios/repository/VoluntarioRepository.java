package cl.duoc.corpaliv.voluntarios.repository;

import cl.duoc.corpaliv.voluntarios.model.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {
}
