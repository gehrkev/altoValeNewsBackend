package com.ajmv.altoValeNewsBackend.model;

import java.sql.Date;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Table(name = "publicacao")
@Entity(name = "publicacao")
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="publicacaoId")
public class Publicacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer publicacaoId;
	private Integer editorId;
	private String titulo;
	private Date data;
	private String texto;
	private byte[] imagem; 
	private byte[] video; 
	private String categoria; 
	private boolean visibilidadeVip;
	private Integer curtidas;
	
	//TODO
	//List<Comentario> comentarios
	public Integer getPublicacaoId() {
		return publicacaoId;
	}
	public void setPublicacaoId(Integer publicacaoId) {
		this.publicacaoId = publicacaoId;
	}
	public Integer getEditorId() {
		return editorId;
	}
	public void setEditorId(Integer editorId) {
		this.editorId = editorId;
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
	public boolean isVisibilidadeVip() {
		return visibilidadeVip;
	}
	public void setVisibilidadeVip(boolean visibilidadeVip) {
		this.visibilidadeVip = visibilidadeVip;
	}
	public Integer getCurtidas() {
		return curtidas;
	}
	public void setCurtidas(Integer curtidas) {
		this.curtidas = curtidas;
	}
	
	
}
