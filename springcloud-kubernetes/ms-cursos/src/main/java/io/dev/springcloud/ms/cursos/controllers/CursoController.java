package io.dev.springcloud.ms.cursos.controllers;

import io.dev.springcloud.ms.cursos.models.entity.Curso;
import io.dev.springcloud.ms.cursos.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CursoController {

    @Autowired
    private CursoService cursoService;


   @GetMapping
    public ResponseEntity<?> listar() {
        List<Curso> cursos = cursoService.listar();
        if(!cursos.isEmpty()) {
            return ResponseEntity.ok(cursos);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable       Long id) {
       Optional<Curso> curso = cursoService.porId(id);
       if(curso.isPresent()) {
           return ResponseEntity.ok(curso);
       }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {
        if(result.hasErrors()) {
            return validar(result);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cursoService.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id, BindingResult result) {
        if(result.hasErrors()) {
            return validar(result);
        }

       Optional<Curso> cursoId = cursoService.porId(id);
       if(cursoId.isPresent()){
           Curso cursodb = cursoId.get();
           cursodb.setNombre(curso.getNombre());
           return ResponseEntity
                   .status(HttpStatus.CREATED)
                   .body(cursoService.guardar(cursodb));
       }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Curso> cursoId = cursoService.porId(id);
        if(cursoId.isPresent()) {
            cursoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage() );
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
