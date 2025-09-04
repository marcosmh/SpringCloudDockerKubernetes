package io.dev.springcloud.ms.cursos.repositories;

import io.dev.springcloud.ms.cursos.models.entity.Curso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {

}
