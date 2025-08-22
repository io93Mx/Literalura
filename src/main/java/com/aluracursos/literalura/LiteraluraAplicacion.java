package com.aluracursos.literalura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aluracursos.literalura.principal.Principal;

@SpringBootApplication
public class LiteraluraAplicacion implements CommandLineRunner{

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
