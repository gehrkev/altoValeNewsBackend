package com.ajmv.altoValeNewsBackend.repository;

import com.ajmv.altoValeNewsBackend.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    List<Comentario> findByPublicacaoId(Integer publicacaoId);
}
