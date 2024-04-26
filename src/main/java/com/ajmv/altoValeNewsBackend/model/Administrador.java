package com.ajmv.altoValeNewsBackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Table(name = "administrador")
@Entity(name = "administrador")
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "admin_id", referencedColumnName = "editor_id")
public class Administrador extends Editor {

}
