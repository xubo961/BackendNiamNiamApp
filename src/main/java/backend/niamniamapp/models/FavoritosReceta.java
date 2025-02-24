package backend.niamniamapp.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "favoritos_receta")
public class FavoritosReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idReceta;

    @Column(nullable = false)
    private String nameReceta;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String ingredientsReceta;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String preparationReceta;

    @Column(nullable = false)
    private String imageReceta;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public String getNameReceta() {
        return nameReceta;
    }

    public void setNameReceta(String nameReceta) {
        this.nameReceta = nameReceta;
    }

    public String getIngredientsReceta() {
        return ingredientsReceta;
    }

    public void setIngredientsReceta(String ingredientsReceta) {
        this.ingredientsReceta = ingredientsReceta;
    }

    public String getPreparationReceta() {
        return preparationReceta;
    }

    public void setPreparationReceta(String preparationReceta) {
        this.preparationReceta = preparationReceta;
    }

    public String getImageReceta() {
        return imageReceta;
    }

    public void setImageReceta(String imageReceta) {
        this.imageReceta = imageReceta;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
}
