package com.aluracursos.literalura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosAutor(
    //String libros,
    @JsonAlias("name") String nombre,
    @JsonAlias("birth_year") Integer fechaNacimiento,
    @JsonAlias("death_year") Integer fechaMuerte
) {

}
