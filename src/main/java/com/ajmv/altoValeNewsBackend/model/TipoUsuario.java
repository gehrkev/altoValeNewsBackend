package com.ajmv.altoValeNewsBackend.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoUsuario {
    USUARIO(0, "USUARIO"),
    USUARIO_VIP(1, "USUARIO_VIP"),
    EDITOR(2, "EDITOR"),
    ADMINISTRADOR(3, "ADMINISTRADOR");

    private final Integer codigo;
    private final String nome;

    TipoUsuario(Integer codigo, String nome){
        this.codigo = codigo;
        this.nome = nome;
    }

    public Integer getCodigo(){
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    @JsonValue
    public Integer toJson() {
        return codigo;
    }

    public static TipoUsuario fromCodigo(Integer codigo) {
        for (TipoUsuario tipo : values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Invalid code for TipoUsuario: " + codigo);
    }
}

