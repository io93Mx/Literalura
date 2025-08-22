package com.aluracursos.literalura.principal;

import java.util.*;
import java.util.stream.Collectors;

import com.aluracursos.literalura.DTO.DatosAutor;
import com.aluracursos.literalura.DTO.Datos;
import com.aluracursos.literalura.DTO.DatosLibro;
import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.service.AutorService;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import com.aluracursos.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private String json;

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public Principal(LibroService libroService, AutorService autorService,
                     LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroService = libroService;
        this.autorService = autorService;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }
    
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

    private void mostrarLibros(List<Libro> libros) {
        libros.forEach(libro -> {
            System.out.println("-------------------------------------------");
            System.out.println("📚 Título:     " + libro.getTitulo());
            System.out.println("👤 Autor:      " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "N/A"));
            System.out.println("🌐 Idioma:     " + libro.getIdioma());
            System.out.println("⬇️ Descargas:  " + libro.getDescargas());
        });
        System.out.println("-------------------------------------------\n");
    }

    private void mostrarAutoreres(List<Autor> autores) {
        autores.forEach(autor -> {
            System.out.println("-------------------------------------------");
            System.out.println("👤 Nombre:         " + autor.getNombre());
            System.out.println("🗓️ Nacimiento:     " + (autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() :
                    "N" +
                    "/A"));
            System.out.println("⚰️ Fallecimiento:  " + (autor.getFechaMuerte() != null ? autor.getFechaMuerte() :
                    "N/A"));
            if (autor.getLibros() != null && !autor.getLibros().isEmpty()) {
                System.out.println("📚 Libros:         " + autor.getLibros().stream()
                        .map(Libro::getTitulo)
                        .collect(Collectors.joining("; ")));
            }
        });
        System.out.println("-------------------------------------------\n");
        
    }

    private void mostrarAutoreresPorAño() {
        System.out.println("\n--- Búsqueda de Autores Vivos ---");
        System.out.println("---------------------------------");
        System.out.print("Ingrese un año positivo: ");
        int anio = leerOpcionEntero();

        List<Autor> autores = autorService.getAllAuthors();
        List<Autor> autoresVivos = autores.stream()
                .filter(autor -> autor.getFechaNacimiento() != null && autor.getFechaNacimiento() <= anio &&
                        (autor.getFechaMuerte() == null || autor.getFechaMuerte() >= anio))
                .collect(Collectors.toList());

        if (autoresVivos.isEmpty()) {
            System.out.println("\n❌ No se encontraron autores vivos en el año " + anio + " en la base de datos.");
        } else {
            System.out.println("\n✅ Autores vivos en el año " + anio + ":");
            mostrarAutoreres(autoresVivos);
        }
        
    }

    private void mostrarLibrosIdioma() {
        System.out.println("\n--- Búsqueda de Libros por Idioma ---");
        System.out.println("-------------------------------------");
        System.out.println("Ingresá el código del idioma (ej. 'es' para español, 'en' para inglés):");
        String idioma = teclado.nextLine().toLowerCase();
        System.out.println("\n🔍 Buscando libros en la base de datos local para el idioma '" + idioma + "'...");
        List<Libro> librosEnBD = libroService.findByLanguage(idioma);

        if (!librosEnBD.isEmpty()) {
            System.out.println("✅ ¡Libros encontrados en la base de datos local! ✅");
            mostrarLibros(librosEnBD);
        } else {
            System.out.println("❌ No se encontraron libros en la base de datos local para este idioma.");
        }

        System.out.println("\n-------------------------------------");
        System.out.println("❓ ¿Quieres buscar también en la API de Gutendex?");
        System.out.println("1. Sí, buscar en la API");
        System.out.println("2. No, volver al menú principal");
        System.out.print("Ingresa tu opción: ");

        int opcionAPI = leerOpcionEntero();
        if (opcionAPI == 1) {
            System.out.println("\n⏳ Buscando en la API de Gutendex para el idioma '" + idioma + "'...");
            String json = consumoApi.obtenerDatos(URL + "?languages=" + idioma);
            DatosRespuestaDTO respuestaDTO = conversor.obtenerDatos(json, DatosRespuestaDTO.class);

            List<DatosLibro> librosAPI = respuestaDTO.getResults();
            if (librosAPI == null || librosAPI.isEmpty()) {
                System.out.println("❌ No se encontraron libros en la API para este idioma.");
                return;
            }

            System.out.println("\n✅ ¡Libros encontrados en la API! ✅");
            procesarLibrosDesdeAPI(librosAPI, "idioma");

        } else {
            System.out.println("Volviendo al menú principal.");
        }
    }


    private int leerOpcionEntero() {
        try {
            int opcion = teclado.nextInt();
            teclado.nextLine();
            return opcion;
        } catch (InputMismatchException e) {
            System.out.println("❌ Entrada inválida. Se esperaba un número.");
            teclado.nextLine();
            return -1;
        }
    }
    
}