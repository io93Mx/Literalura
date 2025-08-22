package com.aluracursos.literalura.model;

import com.aluracursos.literalura.DTO.DatosLibro;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record Libro(
    @JsonAlias("title") String titulo,
    @JsonAlias("languages") String idioma,
    @JsonAlias("name") String autor,
    @JsonAlias("download_count") Integer descargas,
    @JsonAlias("subjects") String genero,
    @JsonAlias("summaries") String sinopsis
) {

    // public Libro() {}

    // @Override
    // public Libro(DatosLibro datos, Autor autor) {
    //     this.titulo = datos.titulo();
    //     this.autor = autor;
    //     this.idioma = datos.idioma.get(0);
    //     this.descargas = datos.descargas();
    // }





}
