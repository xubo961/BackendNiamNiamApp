package backend.niamniamapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "favoritos_receta")
public class FavoritosReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idReceta;

    @Column
    private String nameReceta;

    @Column
    private String ingredientsReceta;

    @Column
    private String preparationReceta;

    @Column
    private String imageReceta;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "favoritos", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Users> listUser;

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

    public List<Users> getListUser() {
        return listUser;
    }

    public void setListUser(List<Users> listUser) {
        this.listUser = listUser;
    }
}
