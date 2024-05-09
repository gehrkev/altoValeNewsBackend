package com.ajmv.altoValeNewsBackend.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Integer>{
    List<Publicacao> findByEditorId(Integer editorId);
}