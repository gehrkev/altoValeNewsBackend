package com.ajmv.altoValeNewsBackend.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "publicacao")
@Entity(name = "publicacao")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "publicacaoId")
public class Publicacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer publicacaoId;

    @ManyToOne
    @JoinColumn(name = "editor_id")
    private Editor editor;

    private String titulo;
    private LocalDate data;
    private String texto;

    @OneToOne
    @JoinColumn(name = "imagem_id")
    private MediaFile imagem;

    @OneToOne
    @JoinColumn(name = "video_id")
    private MediaFile video;

    private String categoria;
    private Boolean visibilidadeVip;
    private Integer curtidas;

    @OneToMany(mappedBy = "publicacaoId")
    private List<Comentario> comentarios;

    public Integer getPublicacaoId() {
        return publicacaoId;
    }

    public void setPublicacaoId(Integer publicacaoId) {
        this.publicacaoId = publicacaoId;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public MediaFile getImagem() {
        return imagem;
    }

    public void setImagem(MediaFile imagem) {
        this.imagem = imagem;
    }

    public MediaFile getVideo() {
        return video;
    }

    public void setVideo(MediaFile video) {
        this.video = video;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean isVisibilidadeVip() {
        return visibilidadeVip;
    }

    public void setVisibilidadeVip(Boolean visibilidadeVip) {
        this.visibilidadeVip = visibilidadeVip;
    }

    public Integer getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(Integer curtidas) {
        this.curtidas = curtidas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }


}
