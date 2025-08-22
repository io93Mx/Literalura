package com.aluracursos.literalura.principal;// package com.aluracursos.literalura.principal;

// import java.util.*;
// import java.util.stream.Collectors;
// import com.aluracursos.literalura.DTO.DatosAutor;
// import com.aluracursos.literalura.DTO.Datos;
// import com.aluracursos.literalura.DTO.DatosLibro;
// import com.aluracursos.literalura.model.Autor;
// import com.aluracursos.literalura.model.Libro;
// import com.aluracursos.literalura.service.ConsumoAPI;
// import com.aluracursos.literalura.service.ConvierteDatos;

// public class Principal2 {
    
//     private Scanner teclado = new Scanner(System.in);
//     private ConsumoAPI consumoApi = new ConsumoAPI();
//     private final String URL = "https://gutendex.com/books/";
//     private ConvierteDatos conversor = new ConvierteDatos();
//     private String json;

//     String menu = """
//         ***Seleccione una de las siguientes opciones***
//         1 - Buscar libro por titulo
//         2 - Ver libros registrados
//         3 - Ver autores registrados
//         4 - Ver autores vivos en un año determinado
//         5 - Ver libros por idioma                 
//         0 - Salir
//         """;
    
//     public void muestraElMenu() {
//         var opcion = -1;
//         while (opcion != 0) {
//             json = consumoApi.obtenerDatos(URL);
//             System.out.println(menu);
//             opcion = teclado.nextInt();
//             teclado.nextLine();
//             switch (opcion) {
//                 case 1 -> buscarLibroTitulo();                
//                 case 2 -> ; 
//                 case 3 -> ; 
//                 case 4 -> ; 
//                 case 5 -> ;
//                 case 0 -> System.out.println("Estas saliendo, nos vemos");
//                 default -> System.out.println("Opcion invalida"); 
                
//             }
            
//         }
//     }

//     private void buscarLibroTitulo() {
//         //crear metodo secundario recibirDatosLibro, 
//         //los datos del api, los pasamos a datosRecord, libros y autores
//         DatosLibro datosLibro = recibirDatosLibro();
//         /// terminamos de escribir los records, datos y datosLibro
//         /// 
//         /// si no esta ese libro que se buscoo si es null
//         if (datosLibro != null) {
//             ///no inicializamos el libro, debemos investigar si nuestro nombre ya esta en nuestra base de datos para agregarle el libro
//             Libro libro;
//             DatosAutor datosAutor = datosLibro.nombre().get(0);
//             Autor autorExistente = autorRepositorio.findByNombre(datosAutor.nombre());

//             if (autorExistente != null) {
//                 libro = new Libro(datosLibro, autorExistente);
//             } else {
//                 Autor nuevoAutor = new Autor(datosAutor);
//                 libro = new Libro(datosLibro, nuevoAutor);
//                 autorRepositorio.save(nuevoAutor);
//             }

//             try {
//                 libroRepositorio.save(libro);
//                 System.out.println(libro);
//             } catch (Exception e) {
//                 System.out.println("Error, el libro ya existe");
//             }
//         } else {
//             System.out.println("No se encontro el libro. :(");
//         }
                
//     }

//     private DatosLibro recibirDatosLibro() {
//         ///obtener los datos filtrados de un libro
//         System.out.println("Que libro deseas buscar");
//         var nombreLibro = teclado.nextLine();
//         ///consultar la api con la busqueda del usuario
//         json = consumoApi.obtenerDatos(URL + "?serach=" + nombreLibro.replace(" ", "+"));
//         /// convertimos los datos del json de la api a nuestro record datos
//         Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);
//         ///muestra toda la lista de libros de la api, debemos 
//         ///establecer filtro de los atributos del libro de nuestra clase para el primer resultado, nombre y libro
//         /// implementando resultados del record Datos.java, que es como un get de los datos de results
//         /// muestra error por que necesita opcional, ya que puede o no existir el titulo buscado
//         /// antes: DatosLibro libroBuscado = datosBusqueda.resultados()...
//         Optional <DatosLibro> libroBuscado = datosBusqueda.resultados().stream().filter(libro -> libro.titulo().toUpperCase().contains(nombreLibro.toUpperCase())).findFirst();
//         ///para que sean mayusculas y se busquen en mayusculas, tambien puede ser lowerCase
//         /// si tenemos opcional debemos tener if, xq solo podmos trabajr con el libro, si, existe

//         ///si existe el libro buscado
//         if (libroBuscado.isPresent()) {
//             ///enviamos
//             return libroBuscado.get();
//         } else {
//             return null;
//         }
//         /// es igual que
//         /// return libroBuscado.orElse(null);



//     }
// }
