package br.edu.utfpr.td.tsi.agencia.digital.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class UsuarioDTO {
	
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

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    @NotBlank(message = "Confirmação de senha é obrigatória")
    private String confirmarSenha;

    private boolean status;    
    private LocalDate dataCadastro;
    
    
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
	
	public String getPassword() {
		return password;
	}
	
	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public boolean isStatus() {
		return status;
	}
	
	public LocalDate getDataCadastro() {
		return dataCadastro;
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
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

}