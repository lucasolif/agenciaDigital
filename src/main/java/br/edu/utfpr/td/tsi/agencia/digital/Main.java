package br.edu.utfpr.td.tsi.agencia.digital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "farias.oliveira.lucas.agencia.digital.model")
public class Main {
	
	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/agenciaDigital"); 
		SpringApplication.run(Main.class, args);
	}

}
