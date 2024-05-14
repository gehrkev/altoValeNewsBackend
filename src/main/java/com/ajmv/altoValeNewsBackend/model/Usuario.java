package com.ajmv.altoValeNewsBackend.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "usuario")
@Entity(name = "usuario")
@Inheritance(strategy=InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="userId")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id")
    protected Integer userId;
    protected String nome;
    protected String sobrenome;
    protected String email;
    protected String cpf;
    protected String endereco;
    protected String cidade;
    protected String estado;
    protected String cep;
    protected String senhahash;
    @Enumerated(EnumType.ORDINAL)
    protected TipoUsuario tipo;

    @Transient // indica ao JPA que este campo não deve ser persistido - somente usado no objeto java para fazer comparações na hora do login com o hash.
    protected String senha;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getSenhahash() {
        return senhahash;
    }

    public void setSenhahash(String senhahash) {
        this.senhahash = senhahash;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
}