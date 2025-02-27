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

    // Obtener todas las recetas favoritas
    public List<FavoritosReceta> obtenerTodosFavoritos() {
        return favoritosRecetaRepository.findAll();
    }

    // Obtener una receta favorita por su ID
    public Optional<FavoritosReceta> obtenerFavoritosPorId(Long id) {
        return favoritosRecetaRepository.findById(id);
    }

    // Obtener recetas favoritas de un usuario específico
    public List<FavoritosReceta> obtenerFavoritosPorUsuario(Long usuarioId) {
        Optional<Users> user = usersRepository.findById(usuarioId);
        if (user.isPresent()) {
            return user.get().getFavoritos(); // Asumimos que tienes un método getFavoritos() en Users
        }
        return null; // O lanzar una excepción si el usuario no existe
    }

//    // Agregar una receta a los favoritos de un usuario
//    @Transactional
//    public FavoritosReceta agregarRecetaAFavoritos(Long usuarioId, Long recetaId) {
//        Optional<Users> user = usersRepository.findById(usuarioId);
//        Optional<FavoritosReceta> receta = favoritosRecetaRepository.findById(recetaId);
//
//        if (user.isPresent() && receta.isPresent()) {
//            Users existingUser = user.get();
//            FavoritosReceta existingReceta = receta.get();
//
//            // Verificar si la receta ya está en los favoritos del usuario
//            if (!existingUser.getFavoritos().contains(existingReceta)) {
//                existingUser.getFavoritos().add(existingReceta);
//                usersRepository.save(existingUser);
//            }
//            return existingReceta;
//        }
//        return null;  // O lanzar una excepción si el usuario o la receta no existen
//    }
//
//
//    // Crear una receta favorita evitando duplicados
//    public FavoritosReceta crearFavoritosReceta(FavoritosReceta favoritosReceta) {
//        if (favoritosRecetaRepository.findByIdReceta(favoritosReceta.getIdReceta()).isPresent()) {
//            throw new IllegalStateException("La receta ya está en favoritos");
//        }
//        return favoritosRecetaRepository.save(favoritosReceta);
//    }

    // Agregar una receta a los favoritos de un usuario
    @Transactional
    public FavoritosReceta agregarRecetaAFavoritos(Long usuarioId, FavoritosReceta favoritosReceta) {
        Optional<Users> user = usersRepository.findById(usuarioId);
        Optional<FavoritosReceta> receta = favoritosRecetaRepository.findById(favoritosReceta.getId());

        if (user.isPresent() && receta.isPresent()) {
            Users existingUser = user.get();
            FavoritosReceta existingReceta = receta.get();

            // Verificar si la receta ya está en los favoritos del usuario
            if (!existingUser.getFavoritos().contains(existingReceta)) {
                existingUser.getFavoritos().add(existingReceta);
                favoritosRecetaRepository.save(favoritosReceta);
                usersRepository.save(existingUser);
            }
            return existingReceta;
        }
        return null;  // O lanzar una excepción si el usuario o la receta no existen
    }

    // Eliminar una receta de los favoritos de un usuario
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
        return false; // O lanzar una excepción si el usuario o receta no existen
    }

    // Actualizar una receta favorita
    public FavoritosReceta actualizarFavoritosReceta(Long id, FavoritosReceta nuevosDatos) {
        Optional<FavoritosReceta> favoritosRecetaOptional = favoritosRecetaRepository.findById(id);

        if (favoritosRecetaOptional.isPresent()) {
            FavoritosReceta favoritosReceta = favoritosRecetaOptional.get();
            favoritosReceta.setIdReceta(nuevosDatos.getIdReceta());
            favoritosReceta.setNameReceta(nuevosDatos.getNameReceta());
            favoritosReceta.setIngredientsReceta(nuevosDatos.getIngredientsReceta());
            favoritosReceta.setPreparationReceta(nuevosDatos.getPreparationReceta());
            favoritosReceta.setImageReceta(nuevosDatos.getImageReceta());
            return favoritosRecetaRepository.save(favoritosReceta);
        }
        return null;  // O lanzar excepción si no se encuentra la receta favorita
    }

    // Eliminar una receta favorita
    public boolean eliminarFavoritosReceta(Long id) {
        Optional<FavoritosReceta> favoritosRecetaOptional = favoritosRecetaRepository.findById(id);
        if (favoritosRecetaOptional.isPresent()) {
            favoritosRecetaRepository.deleteById(id);
            return true;
        }
        return false;  // O lanzar una excepción si no se encuentra la receta favorita
    }
}
