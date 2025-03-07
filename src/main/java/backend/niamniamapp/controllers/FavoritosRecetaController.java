package backend.niamniamapp.controllers;

import backend.niamniamapp.models.FavoritosReceta;
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

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FavoritosReceta>> obtenerFavoritosPorUsuario(@PathVariable Long usuarioId) {
        List<FavoritosReceta> favoritosRecetas = favoritosRecetaService.obtenerFavoritosPorUsuario(usuarioId);
        if (favoritosRecetas != null) {
            return new ResponseEntity<>(favoritosRecetas, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

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

    @DeleteMapping("/usuario/{usuarioId}/receta/{recetaId}")
    public ResponseEntity<Void> eliminarRecetaDeFavoritos(@PathVariable Long usuarioId, @PathVariable Long recetaId) {
        boolean eliminado = favoritosRecetaService.eliminarRecetaDeFavoritos(usuarioId, recetaId);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
