package backend.niamniamapp.controllers;

import backend.niamniamapp.models.FavoritosReceta;
import backend.niamniamapp.services.FavoritosRecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http://localhost:8080/api/favoritos/user/3/add

@RestController
@RequestMapping("/favoritos")
public class FavoritosRecetaController {

    @Autowired
    private FavoritosRecetaService favoritosRecetaService;

    @PostMapping("/user/{userId}/add")
    public ResponseEntity<FavoritosReceta> addFavorito(
            @PathVariable Long userId,
            @RequestBody FavoritosReceta favorito) {
        FavoritosReceta newFavorito = favoritosRecetaService.addFavorito(userId, favorito);
        return ResponseEntity.ok(newFavorito);
    }

    @DeleteMapping("/{favoritoId}")
    public ResponseEntity<Void> deleteFavorito(@PathVariable Long favoritoId) {
        favoritosRecetaService.deleteFavorito(favoritoId);
        return ResponseEntity.noContent().build();
    }
}
