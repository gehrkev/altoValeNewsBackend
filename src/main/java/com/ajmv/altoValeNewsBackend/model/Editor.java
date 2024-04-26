package com.ajmv.altoValeNewsBackend.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Table(name = "editor")
@Entity(name = "editor")
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "editor_id", referencedColumnName = "vip_id")
@Inheritance(strategy= InheritanceType.JOINED)
public class Editor extends UsuarioVIP{

}