package com.ajmv.altoValeNewsBackend.controller;


import com.ajmv.altoValeNewsBackend.model.TipoUsuario;
import com.ajmv.altoValeNewsBackend.model.UsuarioVIP;
import com.ajmv.altoValeNewsBackend.repository.UsuarioVIPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuario/vip") // localhost:8080/usuario_vip
public class UsuarioVIPController {

    @Autowired
    private UsuarioVIPRepository repository;

    @GetMapping // mandar um GET para esse controller cai aqui - Isso substitui aquela query enviada em formato de string 'select * from usuario_vip" etc
    public List<UsuarioVIP> getAll() {
        List<UsuarioVIP> usuarioVIPList = repository.findAll();
        return usuarioVIPList;
    }

    @GetMapping("/{id}") // endpoint para obter um usuário_vip pelo ID
    public ResponseEntity<UsuarioVIP> getById(@PathVariable Integer id){
        Optional<UsuarioVIP> usuarioVIPOptional = repository.findById(id);
        if (usuarioVIPOptional.isPresent()) {
            return ResponseEntity.ok(usuarioVIPOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping // POST - criar um novo usuário
    public ResponseEntity<UsuarioVIP> createUsuarioVIP(@RequestBody UsuarioVIP novoUsuarioVIP) {
        try {
            novoUsuarioVIP.setTipo(TipoUsuario.USUARIO_VIP); // Definir o tipo como tipo 1 - USUARIO VIP
            UsuarioVIP usuarioVIPCriado = repository.save(novoUsuarioVIP);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioVIPCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}") // endpoint para atualizar um usuário pelo ID
    public ResponseEntity<UsuarioVIP> updateUsuarioVIP(@PathVariable Integer id, @RequestBody UsuarioVIP usuarioAtualizado) {
        try {
            Optional<UsuarioVIP> usuarioOptional = repository.findById(id);
            if (usuarioOptional.isPresent()) {
                UsuarioVIP usuarioExistente = usuarioOptional.get();

                usuarioExistente.setAtivo(usuarioAtualizado.isAtivo());
                usuarioExistente.setDataRenovacao(usuarioAtualizado.getDataRenovacao());

                UsuarioVIP usuarioAtualizadoBanco = repository.save(usuarioExistente);
                return ResponseEntity.ok(usuarioAtualizadoBanco);
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o usuário não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
        }
    }

    @PatchMapping("/{id}") // endpoint para atualizar parcialmente um usuário pelo ID
    public ResponseEntity<UsuarioVIP> partialUpdateUsuarioVIP(@PathVariable Integer id, @RequestBody UsuarioVIP usuarioAtualizado) {
        try {
            Optional<UsuarioVIP> usuarioOptional = repository.findById(id);
            if (usuarioOptional.isPresent()) {
                UsuarioVIP usuarioExistente = usuarioOptional.get();
                if (usuarioAtualizado.isAtivo() != null) {
                    usuarioExistente.setAtivo(usuarioAtualizado.isAtivo());
                }
                if (usuarioAtualizado.getDataRenovacao() != null) {
                    usuarioExistente.setDataRenovacao(usuarioAtualizado.getDataRenovacao());
                }

                UsuarioVIP usuarioAtualizadoSalvo = repository.save(usuarioExistente);
                return ResponseEntity.ok(usuarioAtualizadoSalvo);
            } else {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o usuário não for encontrado
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
        }
    }

}

