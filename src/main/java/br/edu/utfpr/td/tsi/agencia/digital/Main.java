package br.edu.utfpr.td.tsi.agencia.digital;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;
import br.edu.utfpr.td.tsi.agencia.digital.services.IndexadorReportagem;
import br.edu.utfpr.td.tsi.agencia.digital.services.ReportagemServices;


@SpringBootApplication
@EntityScan(basePackages = "farias.oliveira.lucas.agencia.digital.model")
public class Main {
	
	@Autowired
	private ReportagemServices reportagemService;

	@Autowired
	private IndexadorReportagem indexadorReportagem;
	
	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/agenciaDigital"); 
		SpringApplication.run(Main.class, args);
	}
	
	@PostConstruct
	public void indexarReportagem() {
		List<Reportagem> reportagens = reportagemService.listar();
		indexadorReportagem.indexar(reportagens);
	}
}
