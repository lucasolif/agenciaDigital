package br.edu.utfpr.td.tsi.agencia.digital.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "Jornalistas")
public class Jornalista {

    @Id
    private String id;
       
    @NotBlank(message = "Campo obrigatório")
    private String nome;
    
    @NotBlank(message = "Campo obrigatório")
    private String cpf;
    
    @NotNull(message = "Campo obrigatório")
    private LocalDate dataNascimento;
    
    private String rg;
    
    @NotBlank(message = "Campo obrigatório")
    private String sexo;
    
    @NotBlank(message = "Campo obrigatório")
    private String celular;
    
    private String telefone;
    
    @NotBlank(message = "Campo obrigatório")
    private String email;
    
    private boolean ativo;
    
    @NotNull(message = "Campo obrigatório")
    private Endereco endereco;
    

    
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getRg() {
        return rg;
    }

    public String getSexo() {
        return sexo;
    }

    public String getCelular() {
        return celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
