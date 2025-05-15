package br.edu.utfpr.td.tsi.agencia.digital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping(value = "/inicio")
	public String telaInicial() { 
	    return "formPaginaInicial";
	}
	
}
