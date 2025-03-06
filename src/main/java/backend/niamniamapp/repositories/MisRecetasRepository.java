package backend.niamniamapp.repositories;

import backend.niamniamapp.models.FavoritosReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MisRecetasRepository extends JpaRepository<FavoritosReceta, Long> {
    Optional<FavoritosReceta> findByIdReceta(Long idReceta);
}