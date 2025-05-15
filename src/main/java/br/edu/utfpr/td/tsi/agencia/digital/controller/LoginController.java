package br.edu.utfpr.td.tsi.agencia.digital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginController {
	@GetMapping("/login")
	String login() {
		return "login";
	}
}