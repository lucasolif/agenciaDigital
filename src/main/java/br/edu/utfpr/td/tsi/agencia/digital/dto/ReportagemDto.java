package br.edu.utfpr.td.tsi.agencia.digital.dto;

import java.time.LocalDate;
import java.util.stream.Collectors;

import br.edu.utfpr.td.tsi.agencia.digital.model.Assunto;
import br.edu.utfpr.td.tsi.agencia.digital.model.Jornalista;
import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;

public class ReportagemDto {

	private String id;
    private String titulo;
    private Jornalista jornalista; 
    private String descricao;   
    private String status;
    private LocalDate dataCadastro;
    private String jornalistaId;
    private String assuntosString;
    
    
    public ReportagemDto(Reportagem reportagem) {
        this.titulo = reportagem.getTitulo();
        this.id = reportagem.getId();
        this.status = reportagem.getStatus();
        this.jornalista = reportagem.getJornalista();
        this.assuntosString = reportagem.getAssuntos().stream().map(Assunto::getNome).collect(Collectors.joining(" | "));
    }
    
	public String getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public Jornalista getJornalista() {
		return jornalista;
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
	public void setAssuntosString(String assuntosString) {
		this.assuntosString = assuntosString;
	}

}
