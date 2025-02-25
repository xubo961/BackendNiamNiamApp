package backend.niamniamapp.repositories;

import backend.niamniamapp.models.FavoritosReceta;
import backend.niamniamapp.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritosRecetaRepository extends JpaRepository<FavoritosReceta, Long> {

    List<FavoritosReceta> findByListUserContaining(Users user);

}