package backend.niamniamapp.services;

import backend.niamniamapp.dto.ApiDelivery;
import backend.niamniamapp.dto.LoginResponse;
import backend.niamniamapp.models.Users;
import backend.niamniamapp.repositories.UsersRepository;
import backend.niamniamapp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public List<Users> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Users createUser(Users user) {
        if (this.userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Usuario ya existe");
        }

        Users newUser = new Users();

        newUser.setFirstName(user.getFirstName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(this.passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(newUser);
    }

    public ApiDelivery<LoginResponse> login(String email, String password) {

//       Users optionalUser = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("El usuario no existe"));
        Optional<Users> optionalUser = this.userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return new ApiDelivery<>("User not found", false, 404, null, "not found");
        }

        Users user = optionalUser.get();
        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            return new ApiDelivery<>("Password incorrect", false, 400, null, "password incorrect");
        }

        String token = this.createToken(email);
        LoginResponse login = new LoginResponse(user, token);
        return new ApiDelivery<>("Login success", true, 200, login, "login success");

    }

    public String createToken(String email) {
        return this.jwtUtil.generateToken(email);
    }
}
