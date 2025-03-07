package backend.niamniamapp.controllers;

import backend.niamniamapp.models.MisRecetas;
import backend.niamniamapp.services.MisRecetasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recetas")
public class MisRecetasController {

    private final MisRecetasService misRecetasService;

    public MisRecetasController(MisRecetasService misRecetasService) {
        this.misRecetasService = misRecetasService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MisRecetas>> obtenerMisRecetas(@PathVariable Long usuarioId) {
        List<MisRecetas> recetas = misRecetasService.obtenerMisRecetasPorUsuario(usuarioId);

        if (recetas != null && !recetas.isEmpty()) {
            return ResponseEntity.ok(recetas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add/{usuarioId}")
    public ResponseEntity<MisRecetas> agregarReceta(@PathVariable Long usuarioId, @RequestBody MisRecetas nuevaReceta) {
        MisRecetas receta = misRecetasService.agregarOCrearRecetaAMisRecetas(usuarioId, nuevaReceta);

        if (receta != null) {
            return ResponseEntity.ok(receta);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/usuario/{usuarioId}/receta/{recetaId}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long usuarioId, @PathVariable Long recetaId) {
        boolean eliminado = misRecetasService.eliminarRecetaDeMisRecetas(usuarioId, recetaId);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
