package backend.niamniamapp.controllers;

import backend.niamniamapp.models.MisRecetas;
import backend.niamniamapp.services.MisRecetasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recetas")
public class MisRecetasController {
    private final MisRecetasService misRecetasService;

    @Autowired
    public MisRecetasController(MisRecetasService misRecetasService) {
        this.misRecetasService = misRecetasService;
    }

    @GetMapping("/add/{usuarioId}")
    public ResponseEntity<MisRecetas> crearRecetaAMisRecetas (
            @PathVariable Long usuarioId,
            @RequestBody MisRecetas nuevaReceta) {
        try {
            MisRecetas recetaGuardada = misRecetasService.crearRecetaAMisRecetas(usuarioId, nuevaReceta);

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



}
