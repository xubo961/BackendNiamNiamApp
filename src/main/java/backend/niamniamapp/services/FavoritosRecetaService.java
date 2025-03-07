package backend.niamniamapp.services;

import backend.niamniamapp.models.FavoritosReceta;
import backend.niamniamapp.repositories.FavoritosRecetaRepository;
import backend.niamniamapp.models.Users;
import backend.niamniamapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FavoritosRecetaService {

    private final FavoritosRecetaRepository favoritosRecetaRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public FavoritosRecetaService(FavoritosRecetaRepository favoritosRecetaRepository, UsersRepository usersRepository) {
        this.favoritosRecetaRepository = favoritosRecetaRepository;
        this.usersRepository = usersRepository;
    }

    public List<FavoritosReceta> obtenerFavoritosPorUsuario(Long usuarioId) {
        Optional<Users> user = usersRepository.findById(usuarioId);
        if (user.isPresent()) {
            return user.get().getFavoritos();
        }
        return null;
    }

    @Transactional
    public FavoritosReceta agregarOCrearRecetaAFavoritos(Long usuarioId, FavoritosReceta nuevaReceta) {
        Optional<Users> user = usersRepository.findById(usuarioId);

        if (user.isPresent()) {
            Users existingUser = user.get();

            Optional<FavoritosReceta> recetaExistente = favoritosRecetaRepository.findByIdReceta(nuevaReceta.getIdReceta());
            FavoritosReceta receta;

            if (recetaExistente.isPresent()) {
                receta = recetaExistente.get();
            } else {
                receta = favoritosRecetaRepository.save(nuevaReceta);
            }

            if (!existingUser.getFavoritos().contains(receta)) {
                existingUser.getFavoritos().add(receta);
                usersRepository.save(existingUser);
            }

            return receta;
        }

        return null;
    }


    @Transactional
    public boolean eliminarRecetaDeFavoritos(Long usuarioId, Long recetaId) {
        Optional<Users> user = usersRepository.findById(usuarioId);
        Optional<FavoritosReceta> receta = favoritosRecetaRepository.findById(recetaId);

        if (user.isPresent() && receta.isPresent()) {
            Users existingUser = user.get();
            FavoritosReceta existingReceta = receta.get();

            if (existingUser.getFavoritos().contains(existingReceta)) {
                existingUser.getFavoritos().remove(existingReceta);
                usersRepository.save(existingUser);
                return true;
            }
        }
        return false;
    }
}
