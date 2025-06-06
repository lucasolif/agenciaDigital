package br.edu.utfpr.td.tsi.agencia.digital.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Endereco {

	@NotBlank(message = "Campo obrigatório")
    private String logradouro;
    @NotNull(message = "Campo obrigatório")
    private int numero;
    @NotBlank(message = "Campo obrigatório")
    private String bairro;
    @NotBlank(message = "Campo obrigatório")
    private String cidade;
    @NotBlank(message = "Campo obrigatório")
    private String estado;
    @NotBlank(message = "Campo obrigatório")
    private String cep;
    private String complemento;
    

    public String getLogradouro() {
        return logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}