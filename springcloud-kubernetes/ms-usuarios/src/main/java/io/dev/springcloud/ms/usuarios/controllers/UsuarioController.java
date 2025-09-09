package io.dev.springcloud.ms.usuarios.controllers;

import io.dev.springcloud.ms.usuarios.models.entity.Usuario;
import io.dev.springcloud.ms.usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable(name="id") Long id) {
        Optional<Usuario> usuario = usuarioService.porId(id);
        if(usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if(result.hasErrors()) {
            return validar(result);
        }

        if(!usuario.getEmail().isEmpty() && usuarioService.existePorEmail(usuario.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections
                            .singletonMap("mensaje: ", "Ya existe un usuario con ese email"));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, @PathVariable Long id, BindingResult result) {
        if(result.hasErrors()) {
            return validar(result);
        }

        Optional<Usuario> usuarioId = usuarioService.porId(id);
        if(usuarioId.isPresent()) {
            Usuario usuariodb = usuarioId.get();

            if(!usuario.getEmail().isEmpty() &&
                    !usuario.getEmail().equalsIgnoreCase(usuariodb.getEmail()) &&
                    usuarioService.porEmail(usuario.getEmail()).isPresent()) {
                return ResponseEntity
                        .badRequest()
                        .body(Collections
                                .singletonMap("mensaje: ", "Ya existe un usuario con ese email"));
            }

            usuariodb.setNombre(usuario.getNombre());
            usuariodb.setEmail(usuario.getEmail());
            usuariodb.setPassword(usuario.getPassword());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(usuarioService.guardar(usuariodb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> usuarioId = usuarioService.porId(id);
        if(usuarioId.isPresent()) {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(usuarioService.listarPorIds(ids));
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage() );
        });
        return ResponseEntity.badRequest().body(errores);
    }




}
