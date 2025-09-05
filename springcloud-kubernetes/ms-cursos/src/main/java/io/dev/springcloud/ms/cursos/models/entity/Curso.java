package io.dev.springcloud.ms.cursos.models.entity;

import io.dev.springcloud.ms.cursos.models.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name="nombre")
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursosUsuarios;

    @Transient
    private List<Usuario> usuarios;

    public Curso() {
        cursosUsuarios = new ArrayList<>();
        usuarios = new ArrayList<>();

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addCursoUsuario(CursoUsuario cursoUsuario) {
        cursosUsuarios.add(cursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuario cursoUsuario) {
        cursosUsuarios.remove(cursoUsuario);
    }

    public List<CursoUsuario> getCursosUsuarios() {
        return cursosUsuarios;
    }

    public void setCursosUsuarios(List<CursoUsuario> cursosUsuarios) {
        this.cursosUsuarios = cursosUsuarios;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
