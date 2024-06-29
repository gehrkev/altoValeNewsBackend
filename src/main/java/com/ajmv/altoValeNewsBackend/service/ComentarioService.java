package com.ajmv.altoValeNewsBackend.service;

import com.ajmv.altoValeNewsBackend.model.Comentario;
import com.ajmv.altoValeNewsBackend.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    public List<Comentario> getComentariosByPublicacao_id(Integer publicacao_id) {
        return comentarioRepository.findByPublicacaoId(publicacao_id);
    }
}
