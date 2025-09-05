package io.dev.springcloud.ms.cursos.clients;

import io.dev.springcloud.ms.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {


    @GetMapping("/{id}")
    Usuario detalle(@PathVariable(name="id") Long id);

    @PostMapping("/")
    Usuario crear(@RequestBody Usuario usuario);

}
