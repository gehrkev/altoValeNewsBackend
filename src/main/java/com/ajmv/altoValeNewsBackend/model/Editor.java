package com.ajmv.altoValeNewsBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Table(name = "editor")
@Entity(name = "editor")
@NoArgsConstructor
//@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "editor_id", referencedColumnName = "vip_id")
public class Editor extends UsuarioVIP{

}
