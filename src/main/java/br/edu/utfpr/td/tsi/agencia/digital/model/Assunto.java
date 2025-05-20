package br.edu.utfpr.td.tsi.agencia.digital.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "Assuntos")
public class Assunto {

	@Id
	private String id;

    @Field("Nome")
    @NotBlank(message = "Campo obrigatório")
	private String nome;
    
    @Field("Status")
    private boolean status;
    

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Assunto [nome=" + nome + "]";
	}
	
	
	
}
