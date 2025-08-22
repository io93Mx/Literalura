package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JsonAlias("title") String titulo;
    @JsonAlias("languages") String idioma;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    @JsonAlias("name") Autor autor;
    @JsonAlias("download_count") Integer descargas;
    @JsonAlias("subjects") String genero;
    @JsonAlias("summaries") String sinopsis;

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", descargas=" + descargas +
                ", autor=" + autor ;
    }


    // public Libro() {}

    // @Override
    // public Libro(DatosLibro datos; Autor nombre) {
    //     this.titulo = datos.titulo();
    //     this.nombre = nombre;
    //     this.idioma = datos.idioma.get(0);
    //     this.descargas = datos.descargas();
    // }





}
