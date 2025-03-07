package backend.niamniamapp.services;

import backend.niamniamapp.models.MisRecetas;
import backend.niamniamapp.models.Users;
import backend.niamniamapp.repositories.MisRecetasRepository;
import backend.niamniamapp.repositories.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MisRecetasService {

    private final UsersRepository usersRepository;
    private final MisRecetasRepository misRecetasRepository;

    public MisRecetasService(UsersRepository usersRepository, MisRecetasRepository misRecetasRepository) {
        this.usersRepository = usersRepository;
        this.misRecetasRepository = misRecetasRepository;
    }

    public List<MisRecetas> obtenerMisRecetasPorUsuario(Long usuarioId) {
        Optional<Users> user = usersRepository.findById(usuarioId);
        if (user.isPresent()) {
            return user.get().getMisrecetas();
        }
        return null;
    }


    @Transactional
    public MisRecetas agregarOCrearRecetaAMisRecetas(Long usuarioId, MisRecetas nuevaReceta) {
        Optional<Users> user = usersRepository.findById(usuarioId);

        if (user.isPresent()) {
            Users existingUser = user.get();

            Optional<MisRecetas> recetaExistente = misRecetasRepository.findByIdReceta(nuevaReceta.getIdReceta());
            MisRecetas receta;

            if (recetaExistente.isPresent()) {
                receta = recetaExistente.get();
            } else {
                receta = misRecetasRepository.save(nuevaReceta);
            }

            if (!existingUser.getMisrecetas().contains(receta)) {
                existingUser.getMisrecetas().add(receta);
                usersRepository.save(existingUser);
            }

            return receta;
        }

        return null;
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

        return false;
    }
}
