package com.aluracursos.literalura.principal;

import java.util.*;
import java.util.stream.Collectors;

import com.aluracursos.literalura.DTO.DatosAutor;
import com.aluracursos.literalura.DTO.Datos;
import com.aluracursos.literalura.DTO.DatosLibro;
import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.AutorService;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import com.aluracursos.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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

    public Principal() {}

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ***Seleccione una de las siguientes opciones***
                    1 - Buscar libro por título
                    2 - Ver libros registrados
                    3 - Ver autores registrados
                    4 - Ver autores vivos en un año determinado
                    5 - Ver libros por idioma                 
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = leerOpcionEntero();

            switch (opcion) {
                case 1 -> buscarLibroTitulo();
                case 2 -> mostrarLibros();
                case 3 -> mostrarAutoreres();
                case 4 -> mostrarAutoreresPorAño();
                case 5 -> mostrarLibrosIdioma();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();
        json = consumoApi.obtenerDatos(URL + "?search=" + nombreLibro.replace(" ", "+"));
        var datosDTO = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datosDTO);
    }

    private void mostrarLibros() {
        List<Libro> libros = libroService.getAllBooks();
        mostrarLibros(libros);
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


    private void mostrarAutoreres() {
        List<Autor> autores = autorService.getAllAuthors();
        mostrarAutoreres(autores);
    }

    private void mostrarAutoreres(List<Autor> autores) {
        autores.forEach(autor -> {
            System.out.println("-------------------------------------------");
            System.out.println("👤 Nombre:         " + autor.getNombre());
            System.out.println("🗓️ Nacimiento:     " + (autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() : "N/A"));
            System.out.println("⚰️ Fallecimiento:  " + (autor.getFechaMuerte() != null ? autor.getFechaMuerte() : "N/A"));
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
        System.out.print("Ingrese un año positivo: ");
        int anio = leerOpcionEntero();

        List<Autor> autores = autorService.getAllAuthors();
        List<Autor> autoresVivos = autores.stream()
                .filter(autor -> autor.getFechaNacimiento() != null && autor.getFechaNacimiento() <= anio &&
                        (autor.getFechaMuerte() == null || autor.getFechaMuerte() >= anio))
                .collect(Collectors.toList());

        if (autoresVivos.isEmpty()) {
            System.out.println("\n❌ No se encontraron autores vivos en el año " + anio + ".");
        } else {
            System.out.println("\n✅ Autores vivos en el año " + anio + ":");
            mostrarAutoreres(autoresVivos);
        }
    }

    private void mostrarLibrosIdioma() {
        System.out.println("\n--- Búsqueda de Libros por Idioma ---");
        System.out.println("Ingresá el código del idioma (ej. 'es' para español, 'en' para inglés):");
        String idioma = teclado.nextLine().toLowerCase();

        List<Libro> librosEnBD = libroService.findByLanguage(idioma);
        if (!librosEnBD.isEmpty()) {
            System.out.println("✅ ¡Libros encontrados en la base de datos local! ✅");
            mostrarLibros(librosEnBD);
        } else {
            System.out.println("❌ No se encontraron libros en la base de datos local para este idioma.");
        }

        System.out.println("¿Quieres buscar también en la API de Gutendex?");
        System.out.println("1. Sí, buscar en la API");
        System.out.println("2. No, volver al menú principal");
        System.out.print("Ingresa tu opción: ");

        int opcionAPI = leerOpcionEntero();
        if (opcionAPI == 1) {
            String json = consumoApi.obtenerDatos(URL + "?languages=" + idioma);
            Datos respuestaDTO = conversor.obtenerDatos(json, Datos.class);

            List<DatosLibro> librosAPI = respuestaDTO.resultados();
            if (librosAPI == null || librosAPI.isEmpty()) {
                System.out.println("❌ No se encontraron libros en la API para este idioma.");
                return;
            }

            System.out.println("✅ ¡Libros encontrados en la API! ✅");
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

    private void procesarLibrosDesdeAPI(List<DatosLibro> libros, String tipoBusqueda) {
        for (DatosLibro datosLibro : libros) {
            System.out.println("\n-------------------------------------------");
            System.out.println("✨ Coincidencia API: " + datosLibro.getTitulo());
            System.out.println("📚 Título:     " + datosLibro.getTitulo());
            System.out.println("🌐 Idioma:     " + (datosLibro.getIdioma().isEmpty() ? "N/A" :
                    datosLibro.getIdioma().get(0)));
            System.out.println("⬇️ Descargas:  " + datosLibro.getDescargas());
            if (datosLibro.getAutor() != null && !datosLibro.getAutor().isEmpty()) {
                System.out.println("✍️ Autor(es):   " + datosLibro.getAutor().stream()
                        .map(DatosAutor::nombre)
                        .collect(Collectors.joining(", ")));
            } else {
                System.out.println("✍️ Autor(es):   No disponible");
            }

            System.out.println("--- ¿Qué quieres hacer con este libro? ---");
            System.out.println("1. Guardar en la base de datos ✅");
            System.out.println("2. Ver el siguiente libro ⏭️");
            System.out.println("3. Volver al menú principal ❌");
            System.out.print("Ingresa tu opción: ");

            int opcion = leerOpcionEntero();
            if (opcion == 1) {
                guardarLibro(datosLibro);
                return;
            } else if (opcion == 3) {
                System.out.println("Volviendo al menú principal...");
                return;
            }
        }
        System.out.println("\n--- Fin de la lista de resultados de la API. ---");
    }

    private void guardarLibro(DatosLibro datosLibro) {
        Optional<Libro> libroExiste = libroService.findByTitle(datosLibro.getTitulo());
        if (libroExiste.isPresent()) {
            System.out.println("\n❌ Libro '" + libroExiste.get().getTitulo() + "' ya está registrado.");
        } else {
            Libro libro = new Libro();
            libro.setTitulo(datosLibro.getTitulo());

            try {
                libro.setDescargas(Integer.valueOf(datosLibro.getDescargas()));
            } catch (NumberFormatException e) {
                libro.setDescargas(0); // Valor por defecto si no es un número válido
            }

            libro.setIdioma(datosLibro.getIdioma().isEmpty() ? "Desconocido" : datosLibro.getIdioma().get(0));

            if (datosLibro.getAutor() != null && !datosLibro.getAutor().isEmpty()) {
                Autor autorPrincipal = procesarAutor(datosLibro.getAutor().get(0));
                libro.setAutor(autorPrincipal);
            } else {
                System.out.println("Advertencia: Libro sin información de autor.");
                libro.setAutor(null);
            }

            Libro libroGuardadoEnDB = libroService.saveBook(libro);
            System.out.println("\n✅ Libro guardado exitosamente: ");
            System.out.println("ID_GUTENDEX: " + datosLibro.getId_libro() + " -> " + libroGuardadoEnDB);
        }
    }


    private Autor procesarAutor(DatosAutor autorDTO) {
        Optional<Autor> autorExistente = autorService.findAuthorByName(autorDTO.nombre());
        if (autorExistente.isPresent()) {
            return autorExistente.get();
        } else {
            Autor nuevoAutor = new Autor();
            nuevoAutor.setNombre(autorDTO.nombre());
            nuevoAutor.setFechaNacimiento(autorDTO.fechaNacimiento());
            nuevoAutor.setFechaMuerte(autorDTO.fechaMuerte());
            return autorRepository.save(nuevoAutor);
        }
    }
}
