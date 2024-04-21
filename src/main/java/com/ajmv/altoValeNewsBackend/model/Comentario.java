package com.ajmv.altoValeNewsBackend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import java.awt.*;
import java.sql.Date;

@Table(name = "comentario")
@Entity(name = "comentario")
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="comentarioId")
public class Comentario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comentarioId;
    private Integer publicacaoId;
    private Integer userId;
    private Date data;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

