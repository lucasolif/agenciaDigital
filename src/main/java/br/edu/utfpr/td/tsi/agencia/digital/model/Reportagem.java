package br.edu.utfpr.td.tsi.agencia.digital.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "Reportagens")
public class Reportagem {
	
	@Id
	private String id;
	
    @Field("Titulo")
    private String titulo;	 
    @Field("Jornalista")
    private Jornalista jornalista;    
    @Field("Assuntos")
    private List<Assunto> assuntos;  
    @Field("Descricao")
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

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public void setJornalista(Jornalista jornalista) {
		this.jornalista = jornalista;
	}

	public void setAssuntos(List<Assunto> assuntos) {
		this.assuntos = assuntos;
	}

}
