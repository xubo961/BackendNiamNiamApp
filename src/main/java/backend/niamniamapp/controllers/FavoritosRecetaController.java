package backend.niamniamapp.controllers;

import backend.niamniamapp.models.FavoritosReceta;
import backend.niamniamapp.models.Users;
import backend.niamniamapp.services.FavoritosRecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favoritos")
public class FavoritosRecetaController {

    private final FavoritosRecetaService favoritosRecetaService;

    @Autowired
    public FavoritosRecetaController(FavoritosRecetaService favoritosRecetaService) {
        this.favoritosRecetaService = favoritosRecetaService;
    }

    // Obtener todas las recetas favoritas
    @GetMapping
    public ResponseEntity<List<FavoritosReceta>> obtenerTodosFavoritos() {
        List<FavoritosReceta> favoritosRecetas = favoritosRecetaService.obtenerTodosFavoritos();
        return new ResponseEntity<>(favoritosRecetas, HttpStatus.OK);
    }

    // Obtener una receta favorita por ID
    @GetMapping("/{id}")
    public ResponseEntity<FavoritosReceta> obtenerFavoritosPorId(@PathVariable Long id) {
        Optional<FavoritosReceta> favoritosReceta = favoritosRecetaService.obtenerFavoritosPorId(id);
        return favoritosReceta
                .map(receta -> new ResponseEntity<>(receta, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener todas las recetas favoritas de un usuario específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FavoritosReceta>> obtenerFavoritosPorUsuario(@PathVariable Long usuarioId) {
        List<FavoritosReceta> favoritosRecetas = favoritosRecetaService.obtenerFavoritosPorUsuario(usuarioId);
        if (favoritosRecetas != null) {
            return new ResponseEntity<>(favoritosRecetas, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si el usuario no tiene favoritos o no existe
    }

    // Agregar una receta a los favoritos de un usuario
//    @PostMapping("/usuario/{usuarioId}/receta/{recetaId}")
//    public ResponseEntity<FavoritosReceta> agregarRecetaAFavoritos(@PathVariable Long usuarioId, @PathVariable Long recetaId) {
//        FavoritosReceta favoritosReceta = favoritosRecetaService.agregarRecetaAFavoritos(usuarioId, recetaId);
//        if (favoritosReceta != null) {
//            return new ResponseEntity<>(favoritosReceta, HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si el usuario o la receta no existen
//    }

//    // Crear una receta favorita
//    @PostMapping("/add")
//    public ResponseEntity<?> crearFavoritosReceta(@RequestBody FavoritosReceta favoritosReceta) {
//        FavoritosReceta createdReceta = favoritosRecetaService.crearFavoritosReceta(favoritosReceta);
//
//        if (createdReceta == null) {
//            return new ResponseEntity<>("La receta ya está en favoritos", HttpStatus.CONFLICT);
//        }
//
//        return new ResponseEntity<>(createdReceta, HttpStatus.CREATED);
//    }

//    @PostMapping("/usuario/{usuarioId}/agregar")
//    public ResponseEntity<?> agregarRecetaAFavoritos(
//            @PathVariable Long usuarioId,
//            @RequestBody FavoritosReceta favoritosReceta) {
//        try {
//            FavoritosReceta recetaAgregada = favoritosRecetaService.agregarRecetaAFavoritos(usuarioId, favoritosReceta);
//            if (recetaAgregada != null) {
//                return ResponseEntity.ok(recetaAgregada);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar la receta: " + e.getMessage());
//        }
//    }

    @PostMapping("/add/{usuarioId}")
    public ResponseEntity<FavoritosReceta> agregarOCrearRecetaAFavoritos(
            @PathVariable Long usuarioId,
            @RequestBody FavoritosReceta nuevaReceta) {
        try {
            FavoritosReceta recetaGuardada = favoritosRecetaService.agregarOCrearRecetaAFavoritos(usuarioId, nuevaReceta);

            if (recetaGuardada != null) {
                return ResponseEntity.ok(recetaGuardada);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Eliminar una receta de los favoritos de un usuario
    @DeleteMapping("/usuario/{usuarioId}/receta/{recetaId}")
    public ResponseEntity<Void> eliminarRecetaDeFavoritos(@PathVariable Long usuarioId, @PathVariable Long recetaId) {
        boolean eliminado = favoritosRecetaService.eliminarRecetaDeFavoritos(usuarioId, recetaId);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si no se encuentra la receta o el usuario
    }

    // Actualizar una receta favorita
    @PutMapping("/{id}")
    public ResponseEntity<FavoritosReceta> actualizarFavoritosReceta(@PathVariable Long id, @RequestBody FavoritosReceta nuevosDatos) {
        FavoritosReceta favoritosReceta = favoritosRecetaService.actualizarFavoritosReceta(id, nuevosDatos);
        if (favoritosReceta != null) {
            return new ResponseEntity<>(favoritosReceta, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si la receta no existe
    }

    // Eliminar una receta favorita
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFavoritosReceta(@PathVariable Long id) {
        boolean eliminado = favoritosRecetaService.eliminarFavoritosReceta(id);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si no se encuentra la receta favorita
    }
}
