package com.ajmv.altoValeNewsBackend.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.ajmv.altoValeNewsBackend.service.PublicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ajmv.altoValeNewsBackend.model.Publicacao;
import com.ajmv.altoValeNewsBackend.repository.PublicacaoRepository;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("publicacao")
public class PublicacaoController {

	@Autowired
	private PublicacaoRepository publicacaoRepository;

	@Autowired
	private PublicacaoService publicacaoService;

	private static final Logger LOGGER = Logger.getLogger(PublicacaoController.class.getName());

	@GetMapping
	public List<Publicacao> getAll() {
		return publicacaoRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Publicacao> getPublicacao(@PathVariable Integer id) {
		Publicacao publicacao = publicacaoService.getPublicacao(id);
		System.out.println("fuck");
		if (publicacao == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(publicacao);
	}

	@PostMapping
	public ResponseEntity<Publicacao> uploadPublicacao(
			@RequestParam("editorId") Integer editorId,
			@RequestParam("titulo") String titulo,
			@RequestParam("data") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
			@RequestParam("texto") String texto,
			@RequestParam(value = "imagem", required = false) MultipartFile imageFile,
			@RequestParam(value = "video", required = false) MultipartFile videoFile,
			@RequestParam("categoria") String categoria,
			@RequestParam("visibilidadeVip") Boolean visibilidadeVip,
			@RequestParam("curtidas") Integer curtidas) {
		try {
			Publicacao publicacao = publicacaoService.savePublicacao(editorId, titulo, data, texto, imageFile, videoFile, categoria, visibilidadeVip, curtidas);
			return ResponseEntity.ok(publicacao);
		} catch (IOException e) {
			return ResponseEntity.status(500).build();
		} catch (SQLException e) {
            throw new RuntimeException(e);
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
	public ResponseEntity<Publicacao> updatePublicacao(
			@PathVariable Integer id,
			@RequestParam("editorId") Integer editorId,
			@RequestParam("titulo") String titulo,
			@RequestParam("data") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
			@RequestParam("texto") String texto,
			@RequestParam(value = "imagem", required = false) MultipartFile imageFile,
			@RequestParam(value = "video", required = false) MultipartFile videoFile,
			@RequestParam("categoria") String categoria,
			@RequestParam("visibilidadeVip") Boolean visibilidadeVip,
			@RequestParam("curtidas") Integer curtidas) {
		try {
			Publicacao publicacao = publicacaoService.updatePublicacao(id, editorId, titulo, data, texto, imageFile, videoFile, categoria, visibilidadeVip, curtidas);
			return ResponseEntity.ok(publicacao);
		} catch (IOException | SQLException e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Publicacao> partialUpdatePublicacao(
			@PathVariable Integer id,
			@RequestParam(value = "editorId", required = false) Integer editorId,
			@RequestParam(value = "titulo", required = false) String titulo,
			@RequestParam(value = "data", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
			@RequestParam(value = "texto", required = false) String texto,
			@RequestParam(value = "imagem", required = false) MultipartFile imageFile,
			@RequestParam(value = "video", required = false) MultipartFile videoFile,
			@RequestParam(value = "categoria", required = false) String categoria,
			@RequestParam(value = "visibilidadeVip", required = false) Boolean visibilidadeVip,
			@RequestParam(value = "curtidas", required = false) Integer curtidas) {
		try {
			Publicacao publicacao = publicacaoService.partialUpdatePublicacao(id, editorId, titulo, data, texto, imageFile, videoFile, categoria, visibilidadeVip, curtidas);
			return ResponseEntity.ok(publicacao);
		} catch (IOException | SQLException e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PatchMapping("/{id}/like")
	public ResponseEntity<Publicacao> like(@PathVariable Integer id) {
		try {
			Publicacao publicacao = publicacaoService.likePublicacao(id);
			if (publicacao != null) {
				return ResponseEntity.ok(publicacao);
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
			Publicacao publicacao = publicacaoService.dislikePublicacao(id);
			if (publicacao != null) {
				return ResponseEntity.ok(publicacao);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
