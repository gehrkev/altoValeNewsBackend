package com.ajmv.altoValeNewsBackend.repository;

import com.ajmv.altoValeNewsBackend.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
}
