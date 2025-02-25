package backend.niamniamapp.services;

import backend.niamniamapp.models.FavoritosReceta;
import backend.niamniamapp.models.Users;
import backend.niamniamapp.repositories.FavoritosRecetaRepository;
import backend.niamniamapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritosRecetaService {

    @Autowired
    private FavoritosRecetaRepository favoritosRecetaRepository;

    @Autowired
    private UsersRepository usersRepository;

    // Método para agregar una receta a los favoritos de un usuario
    @Transactional
    public FavoritosReceta agregarAFavoritos(Long userId, Long recetaId) {
        // Buscar el usuario
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Users user = userOptional.get();

        // Buscar la receta en favoritos o crear una nueva
        Optional<FavoritosReceta> favoritosRecetaOptional = favoritosRecetaRepository.findById(recetaId);
        FavoritosReceta favoritosReceta;

        if (favoritosRecetaOptional.isPresent()) {
            // Si la receta ya existe en la base de datos, la obtenemos
            favoritosReceta = favoritosRecetaOptional.get();
        } else {
            // Si no existe, creamos una nueva receta y la agregamos
            favoritosReceta = new FavoritosReceta();
            favoritosReceta.setIdReceta(recetaId);
            favoritosReceta.setNameReceta("Nombre de la receta");  // Esto lo puedes personalizar o buscar en otra tabla
            favoritosReceta.setIngredientsReceta("Ingredientes de la receta");  // Lo mismo aquí
            favoritosReceta.setPreparationReceta("Preparación de la receta");  // Lo mismo
            favoritosReceta.setImageReceta("URL de la imagen de la receta");  // URL de la imagen, si la tienes
        }

        // Agregar la receta a los favoritos del usuario
        favoritosReceta.getListUser().add(user);
        favoritosRecetaRepository.save(favoritosReceta);

        return favoritosReceta;
    }

    // Método para quitar una receta de los favoritos de un usuario
    @Transactional
    public void quitarDeFavoritos(Long userId, Long recetaId) {
        // Buscar el usuario
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Users user = userOptional.get();

        // Buscar la receta en favoritos
        Optional<FavoritosReceta> favoritosRecetaOptional = favoritosRecetaRepository.findById(recetaId);
        if (!favoritosRecetaOptional.isPresent()) {
            throw new RuntimeException("Receta no encontrada en favoritos");
        }
        FavoritosReceta favoritosReceta = favoritosRecetaOptional.get();

        // Eliminar la receta de los favoritos del usuario
        favoritosReceta.getListUser().remove(user);

        // Si no hay más usuarios que tengan esta receta en favoritos, la eliminamos de la base de datos
        if (favoritosReceta.getListUser().isEmpty()) {
            favoritosRecetaRepository.delete(favoritosReceta);
        } else {
            favoritosRecetaRepository.save(favoritosReceta);
        }
    }

    // Método para obtener las recetas favoritas de un usuario
    public List<FavoritosReceta> obtenerFavoritosPorUsuario(Long userId) {
        // Buscar el usuario
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Users user = userOptional.get();

        // Obtener todas las recetas que este usuario tiene en sus favoritos
        return favoritosRecetaRepository.findByListUserContaining(user);
    }
}