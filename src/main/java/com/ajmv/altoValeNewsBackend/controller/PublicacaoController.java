package com.ajmv.altoValeNewsBackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajmv.altoValeNewsBackend.model.Comentario;
import com.ajmv.altoValeNewsBackend.model.Publicacao;
import com.ajmv.altoValeNewsBackend.model.PublicacaoRepository;

@RestController
@RequestMapping("publicacao") // localhost:8080/publicacao -- Enderenço referência para a API
public class PublicacaoController {

	@Autowired
	private PublicacaoRepository publicacaoRepository;

	@GetMapping // mandar um GET para esse controller cai aqui - Isso substitui aquela query
				// enviada em formato de string 'select * from usuario" etc
	public List<Publicacao> getAll() {
		List<Publicacao> publicacaoList = publicacaoRepository.findAll();
		return publicacaoList;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Publicacao> getById(@PathVariable Integer id) {
		Optional<Publicacao> publicacao = publicacaoRepository.findById(id);
		if (publicacao.isPresent()) {
			return ResponseEntity.ok(publicacao.get());
		} else
			return ResponseEntity.notFound().build();
	}
	
	//TODO
	//GetByEditor

	@PostMapping // Post new publicacao
	public ResponseEntity<Publicacao> create(@RequestBody Publicacao publicacao) {
		try {
			Publicacao publicacaoCriada = publicacaoRepository.save(publicacao);
			return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoCriada);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
