package backend.niamniamapp.repositories;

import backend.niamniamapp.models.FavoritosReceta;
import backend.niamniamapp.models.MisRecetas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MisRecetasRepository extends JpaRepository<MisRecetas, Long> {
    Optional<MisRecetas> findByIdReceta(Long idReceta);
}