package com.ajmv.altoValeNewsBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Table(name = "usuario_vip")
@Entity(name = "usuario_vip")
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "vip_id", referencedColumnName = "userId")
@Inheritance(strategy= InheritanceType.JOINED)
public class UsuarioVIP extends Usuario {

    //TODO remover o atributo "ativo" e atualizar a tabela usuario_vip no DB.
    private Boolean ativo;
    private Date dataRenovacao;

    public Boolean isAtivo() {return ativo;}

    public void setAtivo(boolean ativo) {this.ativo = ativo;}

    public Date getDataRenovacao() {return dataRenovacao;}

    public void setDataRenovacao(Date dataRenovacao) {this.dataRenovacao = dataRenovacao;}
}
