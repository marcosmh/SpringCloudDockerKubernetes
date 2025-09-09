package io.dev.springcloud.ms.cursos.controllers;

import feign.FeignException;
import io.dev.springcloud.ms.cursos.models.Usuario;
import io.dev.springcloud.ms.cursos.models.entity.Curso;
import io.dev.springcloud.ms.cursos.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
       Optional<Curso> curso = cursoService.porIdConUsuaros(id);   //cursoService.porId(id);
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

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
       Optional<Usuario> optional;
       try {
           optional =  cursoService.asignarUsuario(usuario,cursoId);
       } catch (FeignException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje: ",
                            "No existe el usuario por el id o error en la comunicación: \n[ " + e.getMessage() +" ] " ));
       }

       if(optional.isPresent()) {
           return ResponseEntity
                   .status(HttpStatus.CREATED)
                   .body(optional.get());
       }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> optional;
        try {
            optional =  cursoService.crearUsuario(usuario,cursoId);
        } catch (FeignException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje: ",
                            "No se pudo crear el usuario o error en la comunicación: \n[ " + e.getMessage() +" ] " ));
        }

        if(optional.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(optional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> optional;
        try {
            optional =  cursoService.eliminarUsuario(usuario,cursoId);
        } catch (FeignException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje: ",
                            "No existe el usuario por el id o error en la comunicación: \n[ " + e.getMessage() +" ] " ));
        }

        if(optional.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(optional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id) {
       cursoService.eliminarCursoUsuarioPorId(id);
       return ResponseEntity.noContent().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage() );
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
