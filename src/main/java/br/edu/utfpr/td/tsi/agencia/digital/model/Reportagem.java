package br.edu.utfpr.td.tsi.agencia.digital.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Document(collection = "Reportagens")
public class Reportagem {
	
	@Id
	private String id;
	
    @Field("Titulo")
    @NotBlank(message = "Campo obrigatório")
    private String titulo;
		 
    @DBRef
    @Field("Jornalista")
    @NotNull(message = "Campo obrigatório")
    private Jornalista jornalista;
    
    @DBRef
    @Field("Assuntos")
    @NotEmpty(message = "Escolha um ou mais assunto")
    private List<Assunto> assuntos;
    
    @Field("Descricao")
    @NotBlank(message = "Campo obrigatório")
    private String descricao;
    
    @Field("Status")
    private String status;
    
    @Field("DataCadastro")
    private LocalDate dataCadastro;

    
    
	public String getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public Jornalista getJornalista() {
		return jornalista;
	}

	public List<Assunto> getAssuntos() {
		return assuntos;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getStatus() {
		return status;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setJornalista(Jornalista jornalista) {
		this.jornalista = jornalista;
	}

	public void setAssuntos(List<Assunto> assuntos) {
		this.assuntos = assuntos;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

}
