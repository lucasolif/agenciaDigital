package br.edu.utfpr.td.tsi.agencia.digital.dto;

import java.time.LocalDate;
import java.util.List;

import br.edu.utfpr.td.tsi.agencia.digital.model.Assunto;
import br.edu.utfpr.td.tsi.agencia.digital.model.Jornalista;

public class ReportagemDTOConsulta {

	private String id;
    private String titulo;
    private Jornalista jornalista; 
    private List<Assunto> assuntos;
    private String descricao;   
    private String status;
    private LocalDate dataCadastro;
    private String jornalistaId;
    private List<String> assuntosIds;
    private String assuntosString;
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
	public String getJornalistaId() {
		return jornalistaId;
	}
	public List<String> getAssuntosIds() {
		return assuntosIds;
	}
	public String getAssuntosString() {
		return assuntosString;
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
	public void setJornalistaId(String jornalistaId) {
		this.jornalistaId = jornalistaId;
	}
	public void setAssuntosIds(List<String> assuntosIds) {
		this.assuntosIds = assuntosIds;
	}
	public void setAssuntosString(String assuntosString) {
		this.assuntosString = assuntosString;
	}

}
