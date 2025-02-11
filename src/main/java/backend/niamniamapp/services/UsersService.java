package backend.niamniamapp.services;

import backend.niamniamapp.models.Users;
import backend.niamniamapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getAllUsers() {
        return this.usersRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return this.usersRepository.findById(id);
    }

    public Optional<Users> getUserByEmail(String email) {
        return this.usersRepository.findByEmail(email);
    }

    public void deleteUserById(Long id) {
        Users userOptional = this.usersRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        this.usersRepository.delete(userOptional);
    }

    public Users createUser(Users user) {
        // Verificar si el usuario ya existe por email
        if (this.usersRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Usuario ya existe");
        }

        // Crear un nuevo objeto de usuario
        Users newUser = new Users();

        newUser.setFirstName(user.getFirstName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // Guardar el nuevo usuario en la base de datos
        return this.usersRepository.save(newUser);
    }
}
