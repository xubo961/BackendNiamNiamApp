package backend.niamniamapp.controllers;

import backend.niamniamapp.models.MisRecetas;
import backend.niamniamapp.services.MisRecetasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recetas")
public class MisRecetasController {

    private final MisRecetasService misRecetasService;

    public MisRecetasController(MisRecetasService misRecetasService) {
        this.misRecetasService = misRecetasService;
    }

    /**
     * Agregar una receta a "Mis Recetas" del usuario.
     */
    @PostMapping("/{usuarioId}")
    public ResponseEntity<MisRecetas> agregarReceta(@PathVariable Long usuarioId, @RequestBody MisRecetas nuevaReceta) {
        MisRecetas receta = misRecetasService.agregarOCrearRecetaAMisRecetas(usuarioId, nuevaReceta);

        if (receta != null) {
            return ResponseEntity.ok(receta);
        } else {
            return ResponseEntity.badRequest().build(); // O lanzar una excepción
        }
    }

    /**
     * Eliminar una receta de "Mis Recetas" del usuario.
     */
    @DeleteMapping("/{usuarioId}/{recetaId}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long usuarioId, @PathVariable Long recetaId) {
        boolean eliminado = misRecetasService.eliminarRecetaDeMisRecetas(usuarioId, recetaId);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); // O lanzar una excepción
        }
    }
}
