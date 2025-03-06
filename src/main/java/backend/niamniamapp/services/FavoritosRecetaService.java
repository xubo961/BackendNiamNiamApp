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

    @Transactional
    public FavoritosReceta agregarOCrearRecetaAFavoritos(Long usuarioId, FavoritosReceta nuevaReceta) {
        Optional<Users> user = usersRepository.findById(usuarioId);

        if (user.isPresent()) {
            Users existingUser = user.get();

            // Verificar si la receta ya existe en el repositorio
            Optional<FavoritosReceta> recetaExistente = favoritosRecetaRepository.findByIdReceta(nuevaReceta.getIdReceta());
            FavoritosReceta receta;

            if (recetaExistente.isPresent()) {
                receta = recetaExistente.get();
            } else {
                // Crear y guardar la nueva receta
                receta = favoritosRecetaRepository.save(nuevaReceta);
            }

            // Agregar la receta a los favoritos del usuario si aún no está
            if (!existingUser.getFavoritos().contains(receta)) {
                existingUser.getFavoritos().add(receta);
                usersRepository.save(existingUser);
            }

            return receta;
        }

        return null;
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
