package br.edu.utfpr.td.tsi.agencia.digital.dto;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioDTOConsulta {
	
	@Id
	private String id;
	
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Celular é obrigatório")
    private String celular;

    @NotBlank(message = "Login é obrigatório")
    private String username;

    private boolean status;    
    
    
	public String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}
	public String getCelular() {
		return celular;
	}
	
	public String getUsername() {
		return username;
	}

	public boolean isStatus() {
		return status;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
