package backend.niamniamapp.controllers;

import backend.niamniamapp.models.FavoritosReceta;
import backend.niamniamapp.services.FavoritosRecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritosRecetaController {

    @Autowired
    private FavoritosRecetaService favoritosRecetaService;

    // Método para agregar una receta a los favoritos de un usuario
    @PostMapping("/usuario/{userId}/add/{recetaId}")
    public ResponseEntity<FavoritosReceta> agregarAFavoritos(
            @PathVariable Long userId,
            @PathVariable Long recetaId) {

        try {
            FavoritosReceta favoritosReceta = favoritosRecetaService.agregarAFavoritos(userId, recetaId);
            return new ResponseEntity<>(favoritosReceta, HttpStatus.CREATED);  // Código 201 para recurso creado
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // En caso de error
        }
    }

    // Método para quitar una receta de los favoritos de un usuario
    @DeleteMapping("/usuario/{userId}/remove/{recetaId}")
    public ResponseEntity<String> quitarDeFavoritos(
            @PathVariable Long userId,
            @PathVariable Long recetaId) {

        try {
            favoritosRecetaService.quitarDeFavoritos(userId, recetaId);
            return new ResponseEntity<>("Receta eliminada de tus favoritos", HttpStatus.OK);  // Código 200 para OK
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la receta de favoritos", HttpStatus.BAD_REQUEST);  // En caso de error
        }
    }

    // Método para obtener las recetas favoritas de un usuario
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<FavoritosReceta>> obtenerFavoritos(
            @PathVariable Long userId) {

        try {
            List<FavoritosReceta> favoritos = favoritosRecetaService.obtenerFavoritosPorUsuario(userId);
            if (favoritos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Si no tiene recetas favoritas, retorna código 204
            }
            return new ResponseEntity<>(favoritos, HttpStatus.OK);  // Código 200 si se encuentran recetas favoritas
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // En caso de error
        }
    }
}
