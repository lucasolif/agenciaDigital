package br.edu.utfpr.td.tsi.agencia.digital.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotNull;

@Document(collection = "Reportagens")
public class Reportagem {
	
	@Id
	private String id;
		 
    @DBRef
    @Field("jornalista")
    @NotNull(message = "O reporter é obrigatório")
    private Jornalista jornalista;
    
    @DBRef
    @Field("assuntos")
    @NotNull(message = "O assunto é obrigatório")
    private List<String> assuntos;
    
    @Field("status")
    private String status;

	public String getId() {
		return id;
	}

	public Jornalista getJornalista() {
		return jornalista;
	}

	public List<String> getAssuntos() {
		return assuntos;
	}

	public String getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setJornalista(Jornalista jornalista) {
		this.jornalista = jornalista;
	}

	public void setAssuntos(List<String> assuntos) {
		this.assuntos = assuntos;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
