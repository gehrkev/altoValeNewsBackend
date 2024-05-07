package com.ajmv.altoValeNewsBackend.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "publicacao")
@Entity(name = "publicacao")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="publicacaoId")
public class Publicacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer publicacaoId;
	@ManyToOne
	@JoinColumn(name = "editor_id") // Nome da coluna que representa a chave estrangeira na tabela de Publicacao
	private Editor editor;
	private String titulo;
	private Date data;
	private String texto;
	private byte[] imagem; 
	private byte[] video; 
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
	public byte[] getImagem() {
		return imagem;
	}
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	public byte[] getVideo() {
		return video;
	}
	public void setVideo(byte[] video) {
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
