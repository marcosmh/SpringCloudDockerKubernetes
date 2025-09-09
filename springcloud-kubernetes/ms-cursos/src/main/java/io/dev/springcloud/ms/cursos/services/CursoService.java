package io.dev.springcloud.ms.cursos.services;

import io.dev.springcloud.ms.cursos.models.Usuario;
import io.dev.springcloud.ms.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService
{
    List<Curso> listar();

    Optional<Curso> porId(Long id);

    Optional<Curso> porIdConUsuaros(Long id);

    Curso guardar(Curso usuario);

    void eliminar(Long id);

    void eliminarCursoUsuarioPorId(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);


}
