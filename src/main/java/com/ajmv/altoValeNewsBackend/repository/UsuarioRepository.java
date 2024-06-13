package com.ajmv.altoValeNewsBackend.repository;

import com.ajmv.altoValeNewsBackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
}
