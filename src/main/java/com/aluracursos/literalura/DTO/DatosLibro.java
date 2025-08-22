package com.aluracursos.literalura.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosLibro(
    @JsonAlias("title") String titulo,
    @JsonAlias("name") List<DatosAutor> autor,
    @JsonAlias("languages") List<String> idioma,
    @JsonAlias("download_count") String descargas,
    @JsonAlias("subjects") String genero,
    @JsonAlias("summaries") String sinopsis
) {

}
