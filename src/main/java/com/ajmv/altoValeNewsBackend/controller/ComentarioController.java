package com.ajmv.altoValeNewsBackend.controller;

import com.ajmv.altoValeNewsBackend.model.Comentario;
import com.ajmv.altoValeNewsBackend.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("comentario") // localhost:8080/comentario -- Endereço de referência para a API
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
        if (comentario.isPresent()) {
            return ResponseEntity.ok(comentario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping //Post new comentario
    public ResponseEntity<Comentario> create(@RequestBody Comentario comentario) {
        try {
            Comentario comentarioCriado = comentarioRepository.save(comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}") // endpoint para excluir um comentario pelo ID
    public ResponseEntity<?> deleteComentario(@PathVariable Integer id) {
        try {
            Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
            if (comentarioOptional.isPresent()) {
                comentarioRepository.deleteById(id);
                return ResponseEntity.noContent().build(); // Retorna 204 No Content
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o comentário não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
        }
    }

    @PutMapping("/{id}") // endpoint para atualizar um comentário pelo ID
    public ResponseEntity<Comentario> updateComentario(@PathVariable Integer id, @RequestBody Comentario comentarioAtualizado) {
        try {
            Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
            if (comentarioOptional.isPresent()) {
                Comentario comentarioExistente = comentarioOptional.get();

                comentarioExistente.setTexto(comentarioAtualizado.getTexto());
                comentarioExistente.setData(comentarioAtualizado.getData());

                Comentario comentarioAtualizadoBanco = comentarioRepository.save(comentarioExistente);
                return ResponseEntity.ok(comentarioAtualizadoBanco);
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o usuário não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
        }
    }

    @PatchMapping("/{id}") // endpoint para atualizar parcialmente um comentário pelo ID
    public ResponseEntity<Comentario> partialUpdateComentario(@PathVariable Integer id, @RequestBody Comentario comentarioAtualizado) {
        try {
            Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
            if (comentarioOptional.isPresent()) {
                Comentario comentarioExistente = comentarioOptional.get();
                if (comentarioAtualizado.getTexto() != null) {
                    comentarioExistente.setTexto(comentarioAtualizado.getTexto());
                }
                if (comentarioAtualizado.getData() != null) {
                    comentarioExistente.setData(comentarioAtualizado.getData());
                }

                Comentario comentarioAtualizadoSalvo = comentarioRepository.save(comentarioExistente);
                return ResponseEntity.ok(comentarioAtualizadoSalvo);
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o usuário não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
        }
    }

    @PatchMapping("/{id}/like") // endpoint para curtir um comentário pelo ID
    public ResponseEntity<Comentario> like(@PathVariable Integer id) {
        try {
            Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
            if (comentarioOptional.isPresent()) {
                Comentario comentarioExistente = comentarioOptional.get();
                int curtidasAtuais = comentarioExistente.getCurtidas();
                curtidasAtuais++;
                comentarioExistente.setCurtidas(curtidasAtuais);

                Comentario comentarioAtualizadoSalvo = comentarioRepository.save(comentarioExistente);
                return ResponseEntity.ok(comentarioAtualizadoSalvo);
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o comentário não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
        }
    }

    @PatchMapping("/{id}/dislike") // endpoint para descurtir um comentário pelo ID
    public ResponseEntity<Comentario> dislike(@PathVariable Integer id) {
        try {
            Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
            if (comentarioOptional.isPresent()) {
                Comentario comentarioExistente = comentarioOptional.get();
                int curtidasAtuais = comentarioExistente.getCurtidas();
                curtidasAtuais--;
                comentarioExistente.setCurtidas(curtidasAtuais);

                Comentario comentarioAtualizadoSalvo = comentarioRepository.save(comentarioExistente);
                return ResponseEntity.ok(comentarioAtualizadoSalvo);
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o comentário não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
        }
    }

}
