package com.aluracursos.literalura;

import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.AutorService;
import com.aluracursos.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aluracursos.literalura.principal.Principal;

@SpringBootApplication
public class LiteraluraAplicacion implements CommandLineRunner{

	private final Principal principal;

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private LibroService libroService;

	@Autowired
	private AutorService autorService;

	public LiteraluraAplicacion(Principal principal) {
		this.principal = principal;
	}


	public static void main(String[] args) {
		SpringApplication.run(LiteraluraAplicacion.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hola mundo Spring");
		
		Principal principal = new Principal();
		principal.muestraElMenu();
	}

}
