package com.ajmv.altoValeNewsBackend.service;

import com.ajmv.altoValeNewsBackend.model.Editor;
import com.ajmv.altoValeNewsBackend.repository.EditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorService {

    @Autowired
    private EditorRepository editorRepository;

    public Editor getEditor(Integer editorId) {
        return editorRepository.findById(editorId).orElse(null);
    }

}
