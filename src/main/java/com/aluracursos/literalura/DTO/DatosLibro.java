package com.aluracursos.literalura.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class DatosLibro {

    @JsonAlias("id")
    private String id_libro;

    @JsonAlias("title")
    private String titulo;

    @JsonAlias("authors")
    private List<DatosAutor> autor;

    @JsonAlias("languages")
    private List<String> idioma;

    @JsonAlias("download_count")
    private String descargas;

    @JsonAlias("subjects")
    private List<String> genero;

    @JsonAlias("summaries")
    private List<String> sinopsis;
}
