package br.edu.utfpr.td.tsi.agencia.digital;

import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

public class config {
	//Serve para suportar metodos DELETE, PUT... Pois por padrão só aceite GET E POST
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
	    return new HiddenHttpMethodFilter();
	}
}
