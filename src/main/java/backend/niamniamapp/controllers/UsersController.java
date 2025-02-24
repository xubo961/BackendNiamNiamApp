package backend.niamniamapp.controllers;

import backend.niamniamapp.dto.ApiDelivery;
import backend.niamniamapp.dto.LoginRequest;
import backend.niamniamapp.dto.LoginResponse;
import backend.niamniamapp.models.Users;
import backend.niamniamapp.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    // localhost:8080/api/users -> si no hay parámetro en GetMapping
    // localhost:8080/api/users/get-users
    @GetMapping("/get-users")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = this.usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //localhost:8080/api/users/create
    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users createUser = this.usersService.createUser(user);
        return ResponseEntity.ok(createUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest credentials) {
        ApiDelivery<LoginResponse> response = this.usersService.login(credentials.getEmail(), credentials.getPassword());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}