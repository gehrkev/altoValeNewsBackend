package com.ajmv.altoValeNewsBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Table(name = "comentario")
@Entity(name = "comentario")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="comentarioId")
public class Comentario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comentarioId;
    @Column(name = "publicacao_id")
    private Integer publicacaoId;
    @ManyToOne
    @JoinColumn(name = "userId") // Nome da coluna que representa a chave estrangeira na tabela de Comentario
    private Usuario usuario;
    private Date data;
    //TODO @incluirDataAtualizacao

    private String texto;
    private Integer curtidas;

    public Integer getComentarioId() {
        return comentarioId;
    }

    public void setComentarioId(Integer comentarioId) {
        this.comentarioId = comentarioId;
    }

    public Integer getPublicacaoId() {
        return publicacaoId;
    }

    public void setPublicacaoId(Integer publicacaoId) {
        this.publicacaoId = publicacaoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(Integer curtidas) {
        this.curtidas = curtidas;
    }
}

