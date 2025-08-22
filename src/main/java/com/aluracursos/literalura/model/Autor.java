package com.aluracursos.literalura.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//para ignorar propiedades que no estamos mapeando
@JsonIgnoreProperties(ignoreUnknown = true)

public record Autor(
    String libros,
    @JsonAlias("name") String autor,
    @JsonAlias("birth_year") Integer fechaNacimiento,
    @JsonAlias("death_year") Integer fechaMuerte
    
    //@jsonPropperty("")
    //jsonAlias es para leer
    //jsonPropperty es para sobreescribir y leer


    //List<DatosLibro> li = new ArrayList<>();
    ) {

}
