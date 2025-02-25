//package backend.niamniamapp.models;
//
//
//import jakarta.persistence.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.sql.Timestamp;
//
//@Entity
//@Table(name = "Recetas")
//public class Recetas {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Column ( name = "Receta_name")
//    private String RecetaName;
//
//
//    @Column(nullable = false)
//    private String RecetaImagen;
//
//    @Column(nullable = false)
//    private String RecetaIntructions;
//
//    @Column(nullable = false)
//    private String Ingrediente;
//
//    @CreationTimestamp
//    private Timestamp createdAt;
//
//    @UpdateTimestamp
//    private Timestamp updatedAt;
//
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getRecetaName() {
//        return RecetaName;
//    }
//
//    public void setRecetaName(String recetaName) {
//        RecetaName = recetaName;
//    }
//
//    public String getRecetaImagen() {
//        return RecetaImagen;
//    }
//
//    public void setRecetaImagen(String recetaImagen) {
//        RecetaImagen = recetaImagen;
//    }
//
//    public String getRecetaIntructions() {
//        return RecetaIntructions;
//    }
//
//    public void setRecetaIntructions(String recetaIntructions) {
//        RecetaIntructions = recetaIntructions;
//    }
//
//    public String getIngrediente() {
//        return Ingrediente;
//    }
//
//    public void setIngrediente(String ingrediente) {
//        Ingrediente = ingrediente;
//    }
//
//    public Timestamp getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Timestamp createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Timestamp getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Timestamp updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//}
