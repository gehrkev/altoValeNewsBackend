package com.ajmv.altoValeNewsBackend.repository;

import com.ajmv.altoValeNewsBackend.model.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Integer>{
//    List<Publicacao> findByEditorEditorId(Integer editorId);
}