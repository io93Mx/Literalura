package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LibroService {

    @Autowired
    private static LibroRepository libroRepository;

    public static List<Libro> getAllBooks() {
        return (List<Libro>) libroRepository.findAll();
    }

    public List<Libro> findAll() {
        return (List<Libro>) libroRepository.findAll();
    }

    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }

    public List<Libro> findByLanguage(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }

    public Libro saveBook(Libro libro) {
        return libroRepository.save(libro);
    }

    public Optional<Libro> findByTitle(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }
}
