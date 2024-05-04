package com.ajmv.altoValeNewsBackend.controller;

import com.ajmv.altoValeNewsBackend.model.Usuario;
import com.ajmv.altoValeNewsBackend.model.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuario") // localhost:8080/usuario
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping // mandar um GET para esse controller cai aqui - Isso substitui aquela query enviada em formato de string 'select * from usuario" etc
    public List<Usuario> getAll() {
        List<Usuario> usuarioList = repository.findAll();
        return usuarioList;
    }

    @GetMapping("/{id}") // endpoint para obter um usuário pelo ID
    public ResponseEntity<Usuario> getById(@PathVariable Integer id) {
        Optional<Usuario> usuarioOptional = repository.findById(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping // POST - criar um novo usuário
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario novoUsuario) {
        try {
            Usuario usuarioCriado = repository.save(novoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}") // endpoint para excluir um usuário pelo ID
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
        try {
            Optional<Usuario> usuarioOptional = repository.findById(id);
            if (usuarioOptional.isPresent()) {
                repository.deleteById(id);
                return ResponseEntity.noContent().build(); // Retorna 204 No Content
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o usuário não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
        }
    }

    @PutMapping("/{id}") // endpoint para atualizar um usuário pelo ID
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizado) {
        try {
            Optional<Usuario> usuarioOptional = repository.findById(id);
            if (usuarioOptional.isPresent()) {
                Usuario usuarioExistente = usuarioOptional.get();

                usuarioExistente.setEndereco(usuarioAtualizado.getEndereco());
                usuarioExistente.setCidade(usuarioAtualizado.getCidade());
                usuarioExistente.setEstado(usuarioAtualizado.getEstado());
                usuarioExistente.setCep(usuarioAtualizado.getCep());

                Usuario usuarioAtualizadoBanco = repository.save(usuarioExistente);
                return ResponseEntity.ok(usuarioAtualizadoBanco);
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o usuário não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
        }
    }

    @PatchMapping("/{id}") // endpoint para atualizar parcialmente um usuário pelo ID
    public ResponseEntity<Usuario> partialUpdateUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizado) {
        try {
            Optional<Usuario> usuarioOptional = repository.findById(id);
            if (usuarioOptional.isPresent()) {
                Usuario usuarioExistente = usuarioOptional.get();
                if (usuarioAtualizado.getEndereco() != null) {
                    usuarioExistente.setEndereco(usuarioAtualizado.getEndereco());
                }
                if (usuarioAtualizado.getCidade() != null) {
                    usuarioExistente.setCidade(usuarioAtualizado.getCidade());
                }
                if (usuarioAtualizado.getEstado() != null) {
                    usuarioExistente.setEstado(usuarioAtualizado.getEstado());
                }
                if (usuarioAtualizado.getCep() != null) {
                    usuarioExistente.setCep(usuarioAtualizado.getCep());
                }
                if (usuarioAtualizado.getTipo() != null) {
                    usuarioExistente.setTipo(usuarioAtualizado.getTipo());
                }
                if (usuarioAtualizado.getSenhahash() != null) {
                    usuarioExistente.setSenhahash(usuarioAtualizado.getSenhahash());
                }

                Usuario usuarioAtualizadoSalvo = repository.save(usuarioExistente);
                return ResponseEntity.ok(usuarioAtualizadoSalvo);
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o usuário não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
        }
    }

}
