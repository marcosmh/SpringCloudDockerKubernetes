package io.dev.springcloud.ms.cursos.controllers;

import io.dev.springcloud.ms.cursos.models.entity.Curso;
import io.dev.springcloud.ms.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> crear(@RequestBody Curso curso) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cursoService.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id) {
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


}
