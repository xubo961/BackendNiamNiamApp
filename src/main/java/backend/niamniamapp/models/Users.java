package backend.niamniamapp.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//@JsonManagedReference

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_favorito_receta",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "receta_id")
    )
    private List<FavoritosReceta> favoritos;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_mis_recetas",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "misrecetas_id")
    )
    private List<MisRecetas> misrecetas;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<FavoritosReceta> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<FavoritosReceta> favoritos) {
        this.favoritos = favoritos;
    }

    public List<MisRecetas> getMisrecetas() {
        return misrecetas;
    }

    public void setMisrecetas(List<MisRecetas> misrecetas) {
        this.misrecetas = misrecetas;
    }
}
