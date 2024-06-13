package com.ajmv.altoValeNewsBackend.controller;

import com.ajmv.altoValeNewsBackend.model.Usuario;
import com.ajmv.altoValeNewsBackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    // mandar um GET para esse controller cai aqui - Isso substitui aquela query enviada em formato de string 'select * from usuario" etc
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
            // Criptografar a senha antes de salvar
            String senhaPlana = novoUsuario.getSenha();
            novoUsuario.setSenhahash(passwordEncoder.encode(senhaPlana));
            // Limpar o campo senha para garantir que não seja persistido no banco de dados
            novoUsuario.setSenha(null);

//            novoUsuario.setTipo(TipoUsuario.USUARIO); // Definir o tipo como tipo 0 - USUARIO
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
    
    @CrossOrigin
    @PatchMapping("/{id}") // endpoint para atualizar parcialmente um usuário pelo ID
    public ResponseEntity<Usuario> partialUpdateUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizado) {
        try {
            Optional<Usuario> usuarioOptional = repository.findById(id);
            if (usuarioOptional.isPresent()) {
                Usuario usuarioExistente = usuarioOptional.get();

                // Verifica se o novo usuário enviado no payload possui uma senha
                if (usuarioAtualizado.getSenha() != null) {
                    // Criptografa a nova senha e define no usuário existente
                    String senhaPlana = usuarioAtualizado.getSenha();
                    usuarioExistente.setSenhahash(passwordEncoder.encode(senhaPlana));

                    // Limpa o campo senha no objeto atualizado para garantir que não seja persistido no banco de dados
                    usuarioAtualizado.setSenha(null);
                }
                // Atualizar outros campos
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
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o usuário não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
        }
    }

}
