package com.ajmv.altoValeNewsBackend.controller;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ajmv.altoValeNewsBackend.model.Publicacao;
import com.ajmv.altoValeNewsBackend.repository.PublicacaoRepository;

@RestController
@RequestMapping("publicacao")
public class PublicacaoController {

	@Autowired
	private PublicacaoRepository publicacaoRepository;
	private static final Logger LOGGER = Logger.getLogger(PublicacaoController.class.getName());


	@GetMapping
	public List<Publicacao> getAll() {
		return publicacaoRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Publicacao> getById(@PathVariable Integer id) {
		Optional<Publicacao> publicacao = publicacaoRepository.findById(id);
		return publicacao.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Publicacao> create(@RequestBody Publicacao publicacao) {
		try {
			// Log the received base64 strings
			if (publicacao.getImagem() != null && publicacao.getImagem().length > 0) {
				LOGGER.info("Received base64 image string: " + new String(publicacao.getImagem()));
				String imagemBase64 = new String(publicacao.getImagem()).replaceAll("\\s+", "");
				Decoder decoder = Base64.getDecoder();
				publicacao.setImagem(decoder.decode(imagemBase64));
			}
			if (publicacao.getVideo() != null && publicacao.getVideo().length > 0) {
				LOGGER.info("Received base64 video string: " + new String(publicacao.getVideo()));
				String videoBase64 = new String(publicacao.getVideo()).replaceAll("\\s+", "");
				Decoder decoder = Base64.getDecoder();
				publicacao.setVideo(decoder.decode(videoBase64));
			}

			Publicacao publicacaoCriada = publicacaoRepository.save(publicacao);
			return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoCriada);
		} catch (IllegalArgumentException e) {
			LOGGER.severe("Failed to decode base64 string: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (Exception e) {
			LOGGER.severe("An unexpected error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePublicacao(@PathVariable Integer id) {
		try {
			Optional<Publicacao> publicacaoOptional = publicacaoRepository.findById(id);
			if (publicacaoOptional.isPresent()) {
				publicacaoRepository.deleteById(id);
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Publicacao> updatePublicacao(@PathVariable Integer id, @RequestBody Publicacao publicacaoAtualizado) {
		try {
			Optional<Publicacao> publicacaoOptional = publicacaoRepository.findById(id);
			if (publicacaoOptional.isPresent()) {
				Publicacao publicacaoExistente = publicacaoOptional.get();

				publicacaoExistente.setTexto(publicacaoAtualizado.getTexto());
				publicacaoExistente.setData(publicacaoAtualizado.getData());
				if (publicacaoAtualizado.getImagem() != null) {
					publicacaoExistente.setImagem(Base64.getDecoder().decode(publicacaoAtualizado.getImagem()));
				}
				if (publicacaoAtualizado.getVideo() != null) {
					publicacaoExistente.setVideo(Base64.getDecoder().decode(publicacaoAtualizado.getVideo()));
				}
				publicacaoExistente.setCategoria(publicacaoAtualizado.getCategoria());
				publicacaoExistente.setVisibilidadeVip(publicacaoAtualizado.isVisibilidadeVip());
				publicacaoExistente.setTitulo(publicacaoAtualizado.getTitulo());

				Publicacao publicacaoAtualizadoBanco = publicacaoRepository.save(publicacaoExistente);
				return ResponseEntity.ok(publicacaoAtualizadoBanco);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Publicacao> partialUpdatePublicacao(@PathVariable Integer id, @RequestBody Publicacao publicacaoAtualizado) {
		try {
			Optional<Publicacao> publicacaoOptional = publicacaoRepository.findById(id);
			if (publicacaoOptional.isPresent()) {
				Publicacao publicacaoExistente = publicacaoOptional.get();

				if (publicacaoAtualizado.getTexto() != null) {
					publicacaoExistente.setTexto(publicacaoAtualizado.getTexto());
				}
				if (publicacaoAtualizado.getData() != null) {
					publicacaoExistente.setData(publicacaoAtualizado.getData());
				}
				if (publicacaoAtualizado.getImagem() != null) {
					publicacaoExistente.setImagem(Base64.getDecoder().decode(publicacaoAtualizado.getImagem()));
				}
				if (publicacaoAtualizado.getVideo() != null) {
					publicacaoExistente.setVideo(Base64.getDecoder().decode(publicacaoAtualizado.getVideo()));
				}
				if (publicacaoAtualizado.getCategoria() != null) {
					publicacaoExistente.setCategoria(publicacaoAtualizado.getCategoria());
				}
				if (publicacaoAtualizado.isVisibilidadeVip() != null) {
					publicacaoExistente.setVisibilidadeVip(publicacaoAtualizado.isVisibilidadeVip());
				}
				if (publicacaoAtualizado.getTitulo() != null) {
					publicacaoExistente.setTitulo(publicacaoAtualizado.getTitulo());
				}

				Publicacao publicacaoAtualizadoSalvo = publicacaoRepository.save(publicacaoExistente);
				return ResponseEntity.ok(publicacaoAtualizadoSalvo);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping("/{id}/like")
	public ResponseEntity<Publicacao> like(@PathVariable Integer id) {
		try {
			Optional<Publicacao> publicacaoOptional = publicacaoRepository.findById(id);
			if (publicacaoOptional.isPresent()) {
				Publicacao publicacaoExistente = publicacaoOptional.get();
				publicacaoExistente.setCurtidas(publicacaoExistente.getCurtidas() + 1);

				Publicacao publicacaoAtualizadaSalva = publicacaoRepository.save(publicacaoExistente);
				return ResponseEntity.ok(publicacaoAtualizadaSalva);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping("/{id}/dislike")
	public ResponseEntity<Publicacao> dislike(@PathVariable Integer id) {
		try {
			Optional<Publicacao> publicacaoOptional = publicacaoRepository.findById(id);
			if (publicacaoOptional.isPresent()) {
				Publicacao publicacaoExistente = publicacaoOptional.get();
				publicacaoExistente.setCurtidas(publicacaoExistente.getCurtidas() - 1);

				Publicacao publicacaoAtualizadaSalva = publicacaoRepository.save(publicacaoExistente);
				return ResponseEntity.ok(publicacaoAtualizadaSalva);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
