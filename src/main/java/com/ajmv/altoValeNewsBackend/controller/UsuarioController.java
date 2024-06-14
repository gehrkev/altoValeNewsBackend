package com.ajmv.altoValeNewsBackend.controller;

import com.ajmv.altoValeNewsBackend.exception.CPFInvalidoException;
import com.ajmv.altoValeNewsBackend.exception.EmailJaCadastradoException;
import com.ajmv.altoValeNewsBackend.model.Usuario;
import com.ajmv.altoValeNewsBackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuario") // localhost:8080/usuario
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public List<Usuario> getAll() {
        List<Usuario> usuarioList = repository.findAll();
        return usuarioList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Integer id) {
        Optional<Usuario> usuarioOptional = repository.findById(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario novoUsuario) {
        try {
            String senhaPlana = novoUsuario.getSenha();
            novoUsuario.setSenhahash(passwordEncoder.encode(senhaPlana));
            novoUsuario.setSenha(null);

            Usuario usuarioCriado = repository.save(novoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
        } catch (DataIntegrityViolationException e) {
            Throwable cause = e.getRootCause();
            if (cause instanceof PSQLException) {
                PSQLException sqlException = (PSQLException) cause;
                if (PSQLState.UNIQUE_VIOLATION.getState().equals(sqlException.getSQLState())) {
                    throw new EmailJaCadastradoException("E-mail já cadastrado.");
                } else if (PSQLState.INVALID_PARAMETER_VALUE.getState().equals(sqlException.getSQLState())) {
                    throw new CPFInvalidoException("CPF inválido.");
                }
            }
            throw new RuntimeException("Erro ao criar usuário.", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
        try {
            Optional<Usuario> usuarioOptional = repository.findById(id);
            if (usuarioOptional.isPresent()) {
                repository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
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
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @CrossOrigin
    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> partialUpdateUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizado) {
        try {
            Optional<Usuario> usuarioOptional = repository.findById(id);
            if (usuarioOptional.isPresent()) {
                Usuario usuarioExistente = usuarioOptional.get();
                if (usuarioAtualizado.getSenha() != null) {
                    String senhaPlana = usuarioAtualizado.getSenha();
                    usuarioExistente.setSenhahash(passwordEncoder.encode(senhaPlana));
                    usuarioAtualizado.setSenha(null);
                }
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

                Usuario usuarioAtualizadoSalvo = repository.save(usuarioExistente);
                return ResponseEntity.ok(usuarioAtualizadoSalvo);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
