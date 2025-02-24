package backend.niamniamapp.dto;

import backend.niamniamapp.models.Users;

public class LoginResponse {
    private long id;
    private String firstName;
    private String email;
    private String password;
    private String token;

    public LoginResponse(Users user, String token) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
