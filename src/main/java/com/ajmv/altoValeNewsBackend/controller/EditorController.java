package com.ajmv.altoValeNewsBackend.controller;

import com.ajmv.altoValeNewsBackend.model.Editor;
import com.ajmv.altoValeNewsBackend.repository.EditorRepository;
import com.ajmv.altoValeNewsBackend.model.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuario/editor") // localhost:8080/usuario/editor
public class EditorController {
    @Autowired
    private EditorRepository repository;

    @GetMapping
    // mandar um GET para esse controller cai aqui - Isso substitui aquela query enviada em formato de string 'select * from editor" etc
    public List<Editor> getAll() {
        List<Editor> editorList = repository.findAll();
        return editorList;
    }

    @GetMapping("/{id}") // endpoint para obter um editor pelo ID
    public ResponseEntity<Editor> getById(@PathVariable Integer id){
        Optional<Editor> editorOptional = repository.findById(id);
        if (editorOptional.isPresent()) {
            return ResponseEntity.ok(editorOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping // POST - criar um novo editor
    public ResponseEntity<Editor> createEditor(@RequestBody Editor novoEditor) {
        try {
            novoEditor.setTipo(TipoUsuario.EDITOR); // Definir o tipo como tipo 2 - EDITOR
            Editor editorCriado = repository.save(novoEditor);
            return ResponseEntity.status(HttpStatus.CREATED).body(editorCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
