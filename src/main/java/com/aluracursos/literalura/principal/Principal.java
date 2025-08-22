package com.aluracursos.literalura.principal;

import java.util.*;
import java.util.stream.Collectors;

import com.aluracursos.literalura.DTO.DatosAutor;
import com.aluracursos.literalura.DTO.Datos;
import com.aluracursos.literalura.DTO.DatosLibro;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private String json;
    
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ***Seleccione una de las siguientes opciones***
                    1 - Buscar libro por titulo
                    2 - Ver libros registrados
                    3 - Ver autores registrados
                    4 - Ver autores vivos en un año determinado
                    5 - Ver libros por idioma                 
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            
            switch (opcion) {
                case 1:
                    buscarLibroTitulo();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    mostrarAutoreres();
                    break;
                case 4:
                    mostrarAutoreresPorAño();
                    break;
                case 5:
                    mostrarLibrosIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }        
    }
        
    ///metodos

    private void buscarLibroTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();        
        json = consumoApi.obtenerDatos(URL + "?search=" + nombreLibro.replace(" ", "+"));
        //System.out.println(json);
        
        var datosDTO = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datosDTO);

        ///obtener datos del json de la api, y convertirlo a la clase DatosLibroDTO
        // DatosLibroDTO datos = conversor.obtenerDatos(json, DatosLibroDTO.class);
        // return datos;
    }

    private void mostrarLibros() {
        
    }

    private void mostrarAutoreres() {
        
    }

    private void mostrarAutoreresPorAño() {
        
    }

    private void mostrarLibrosIdioma() {
        
    }
    
}