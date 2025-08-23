package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends CrudRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllBooks();

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros WHERE (a.fechaMuerte IS NULL OR a.fechaMuerte > :anio) AND a.fechaNacimiento <= :anio")
    List<Autor> findAliveAuthor(@Param("anio") int anio);

    Optional<Autor> findByNombre(String nombre);
}
