package com.ajmv.altoValeNewsBackend.controller;

import com.ajmv.altoValeNewsBackend.model.Comentario;
import com.ajmv.altoValeNewsBackend.model.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("comentario")// localhost:8080/comentario -- Enderenço referência para a API
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @GetMapping // mandar um GET para esse controller cai aqui - Isso substitui aquela query enviada em formato de string 'select * from usuario" etc
    public List<Comentario> getAll() {
        List<Comentario> comentarioList = comentarioRepository.findAll();
        return comentarioList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> getById(@PathVariable Integer id) {
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        if(comentario.isPresent()) {
            return ResponseEntity.ok(comentario.get());
        } else
            return ResponseEntity.notFound().build();
    }

    //TODO    //GetByPublicacao

    @PostMapping //Post new comentario
    public ResponseEntity<Comentario> create(@RequestBody Comentario comentario) {
        try {
            Comentario comentarioCriado = comentarioRepository.save(comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCriado);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //TODO @PutMapping
    //TODO @PatchMapping
    //TODO @DeleteMapping
}
