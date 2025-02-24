package backend.niamniamapp.services;

import backend.niamniamapp.models.FavoritosReceta;
import backend.niamniamapp.models.Users;
import backend.niamniamapp.repositories.FavoritosRecetaRepository;
import backend.niamniamapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritosRecetaService {

    @Autowired
    private FavoritosRecetaRepository favoritosRecetaRepository;

    @Autowired
    private UsersRepository usersRepository;

    public FavoritosReceta addFavorito(Long userId, FavoritosReceta favorito) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        favorito.setUser(user);
        return favoritosRecetaRepository.save(favorito);
    }

    public void deleteFavorito(Long favoritoId) {
        FavoritosReceta favorito = favoritosRecetaRepository.findById(favoritoId)
                .orElseThrow(() -> new RuntimeException("Favorito not found with ID: " + favoritoId));
        favoritosRecetaRepository.delete(favorito);
    }

}

