package io.dev.springcloud.ms.cursos.services;

import io.dev.springcloud.ms.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService
{
    List<Curso> listar();

    Optional<Curso> porId(Long id);

    Curso guardar(Curso usuario);

    void eliminar(Long id);
}
