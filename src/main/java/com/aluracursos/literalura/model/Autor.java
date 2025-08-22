package com.aluracursos.literalura.model;

import java.util.ArrayList;
import java.util.List;

import com.aluracursos.literalura.DTO.DatosAutor;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//para ignorar propiedades que no estamos mapeando
@JsonIgnoreProperties(ignoreUnknown = true)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "autores")
public class Autor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @JsonAlias("name") String nombre;
        @JsonAlias("birth_year") Integer fechaNacimiento;
        @JsonAlias("death_year") Integer fechaMuerte;

        @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Libro> libros = new ArrayList<>();

        //@jsonPropperty("")
        //jsonAlias es para leer
        //jsonPropperty es para sobreescribir y leer


        //List<DatosLibro> li = new ArrayList<>();

        public Autor(DatosAutor autorDTO) {
            this.nombre = String.valueOf(autorDTO.nombre());
            this.fechaNacimiento = Integer.valueOf(autorDTO.fechaNacimiento());
            this.fechaMuerte = Integer.valueOf(autorDTO.fechaMuerte());
        }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Integer getFechaMuerte() {
        return fechaMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    @Override
        public String toString() {
            return
                    " nombre='" + nombre + '\'' +
                            ", nacimiento=" + fechaNacimiento +
                            ", fallecimiento=" + fechaMuerte;
        }
}
