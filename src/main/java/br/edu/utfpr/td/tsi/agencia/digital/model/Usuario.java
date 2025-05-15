package br.edu.utfpr.td.tsi.agencia.digital.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "Usuarios")
public class Usuario {

    @Id
    private String id;

    @NotBlank(message = "Nome do usuário é obrigatório")
    private String nome;
    @NotBlank(message = "Email do usuário é obrigatório")
    @Indexed(unique = true)
    private String email;
    @NotBlank(message = "Celular do usuário é obrigatório")
    private String celular;
    @NotBlank(message = "Login do usuário é obrigatório")
    @Indexed(unique = true)
    private String username;
    @NotBlank(message = "Senha do usuário é obrigatório")
    private String password;

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

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(boolean ativo) {
        this.status = ativo;
    }
   
}
