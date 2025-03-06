package backend.niamniamapp.services;

import backend.niamniamapp.models.MisRecetas;
import backend.niamniamapp.models.Users;
import backend.niamniamapp.repositories.MisRecetasRepository;
import backend.niamniamapp.repositories.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MisRecetasService {

    private final UsersRepository usersRepository;
    private final MisRecetasRepository misRecetasRepository;

    public MisRecetasService(UsersRepository usersRepository, MisRecetasRepository misRecetasRepository) {
        this.usersRepository = usersRepository;
        this.misRecetasRepository = misRecetasRepository;
    }

    @Transactional
    public MisRecetas agregarOCrearRecetaAMisRecetas(Long usuarioId, MisRecetas nuevaReceta) {
        Optional<Users> user = usersRepository.findById(usuarioId);

        if (user.isPresent()) {
            Users existingUser = user.get();

            // Verificar si la receta ya existe en el repositorio por su idReceta
            Optional<MisRecetas> recetaExistente = misRecetasRepository.findByIdReceta(nuevaReceta.getIdReceta());
            MisRecetas receta;

            if (recetaExistente.isPresent()) {
                receta = recetaExistente.get();
            } else {
                // Guardar la nueva receta
                receta = misRecetasRepository.save(nuevaReceta);
            }

            // Agregar la receta a la lista del usuario si aún no está
            if (!existingUser.getMisrecetas().contains(receta)) {
                existingUser.getMisrecetas().add(receta);
                usersRepository.save(existingUser);
            }

            return receta;
        }

        return null; // O lanzar una excepción
    }

    @Transactional
    public boolean eliminarRecetaDeMisRecetas(Long usuarioId, Long recetaId) {
        Optional<Users> user = usersRepository.findById(usuarioId);
        Optional<MisRecetas> receta = misRecetasRepository.findById(recetaId);

        if (user.isPresent() && receta.isPresent()) {
            Users existingUser = user.get();
            MisRecetas existingReceta = receta.get();

            if (existingUser.getMisrecetas().contains(existingReceta)) {
                existingUser.getMisrecetas().remove(existingReceta);
                usersRepository.save(existingUser);
                return true;
            }
        }

        return false; // O lanzar una excepción si no existe
    }
}
