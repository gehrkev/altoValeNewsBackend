package com.ajmv.altoValeNewsBackend.controller;

import java.util.List;
import java.util.Optional;

import com.ajmv.altoValeNewsBackend.model.Comentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ajmv.altoValeNewsBackend.model.Publicacao;
import com.ajmv.altoValeNewsBackend.model.PublicacaoRepository;

@RestController
@RequestMapping("publicacao") // localhost:8080/publicacao -- Enderenço referência para a API
public class PublicacaoController {

	@Autowired
	private PublicacaoRepository publicacaoRepository;

	@GetMapping // mandar um GET para esse controller cai aqui - Isso substitui aquela query enviada em formato de string 'select * from usuario" etc
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

	//TODO Avaliar a necessidade do endpoint - o Spring se confunde na hora de encontrar a chave devido à herança e chaves com nomes diferentes para subclasses
//	@GetMapping("/editor/{editorId}") // endpoint para obter todas as publicações de um editor pelo ID do editor
//	public ResponseEntity<List<Publicacao>> getByEditor(@PathVariable Integer editorId) {
//		try {
//			List<Publicacao> publicacoesDoEditor = publicacaoRepository.findByEditorEditorId(editorId);
//
//			if (!publicacoesDoEditor.isEmpty()) {
//				return ResponseEntity.ok(publicacoesDoEditor);
//			} else {
//				return ResponseEntity.notFound().build(); // Retorna 404 Not Found se nenhuma publicação for encontrada para o editor
//			}
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
//		}
//	}

	@PostMapping // Post new publicacao
	public ResponseEntity<Publicacao> create(@RequestBody Publicacao publicacao) {
		try {
			Publicacao publicacaoCriada = publicacaoRepository.save(publicacao);
			return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoCriada);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/{id}") // endpoint para excluir uma publicação pelo ID
	public ResponseEntity<?> deletePublicacao(@PathVariable Integer id) {
		try {
			Optional<Publicacao> publicacaoOptional = publicacaoRepository.findById(id);
			if (publicacaoOptional.isPresent()) {
				publicacaoRepository.deleteById(id);
				return ResponseEntity.noContent().build(); // Retorna 204 No Content
			} else {
				return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o comentário não for encontrado
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
		}
	}

	@PutMapping("/{id}") // endpoint para atualizar uma publicação pelo ID
	public ResponseEntity<Publicacao> updatePublicacao(@PathVariable Integer id, @RequestBody Publicacao publicacaoAtualizado) {
		try {
			Optional<Publicacao> publicacaoOptional = publicacaoRepository.findById(id);
			if (publicacaoOptional.isPresent()) {
				Publicacao publicacaoExistente = publicacaoOptional.get();

				publicacaoExistente.setTexto(publicacaoAtualizado.getTexto());
				publicacaoExistente.setData(publicacaoAtualizado.getData());
				publicacaoExistente.setImagem(publicacaoAtualizado.getImagem());
				publicacaoExistente.setVideo(publicacaoAtualizado.getVideo());
				publicacaoExistente.setCategoria(publicacaoAtualizado.getCategoria());
				publicacaoExistente.setVisibilidadeVip(publicacaoAtualizado.isVisibilidadeVip());
				publicacaoExistente.setTitulo(publicacaoAtualizado.getTitulo());

				Publicacao publicacaoAtualizadoBanco = publicacaoRepository.save(publicacaoExistente);
				return ResponseEntity.ok(publicacaoAtualizadoBanco);
			} else {
				return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o usuário não for encontrado
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
		}
	}

	@PatchMapping("/{id}") // endpoint para atualizar parcialmente uma publicação pelo ID
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
					publicacaoExistente.setImagem(publicacaoAtualizado.getImagem());
				}
				if (publicacaoAtualizado.getVideo() != null) {
					publicacaoExistente.setVideo(publicacaoAtualizado.getVideo());
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
				return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o usuário não for encontrado
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
		}
	}

	@PatchMapping("/{id}/like") // endpoint para atualizar as curtidas pelo ID
	public ResponseEntity<Publicacao> like(@PathVariable Integer id) {
		try {
			Optional<Publicacao> publicacaoOptional = publicacaoRepository.findById(id);
			if (publicacaoOptional.isPresent()) {
				Publicacao publicacaoExistente = publicacaoOptional.get();
				int curtidasAtuais = publicacaoExistente.getCurtidas();
				curtidasAtuais++;
				publicacaoExistente.setCurtidas(curtidasAtuais);

				Publicacao publicacaoAtualizadaSalva = publicacaoRepository.save(publicacaoExistente);
				return ResponseEntity.ok(publicacaoAtualizadaSalva);
			} else {
				return ResponseEntity.notFound().build(); // Retorna 404 Not Found se a publicacao não for encontrada
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
		}
	}

	@PatchMapping("/{id}/dislike") // endpoint para atualizar as curtidas pelo ID
	public ResponseEntity<Publicacao> dislike(@PathVariable Integer id) {
		try {
			Optional<Publicacao> publicacaoOptional = publicacaoRepository.findById(id);
			if (publicacaoOptional.isPresent()) {
				Publicacao publicacaoExistente = publicacaoOptional.get();
				int curtidasAtuais = publicacaoExistente.getCurtidas();
				curtidasAtuais--;
				publicacaoExistente.setCurtidas(curtidasAtuais);

				Publicacao publicacaoAtualizadaSalva = publicacaoRepository.save(publicacaoExistente);
				return ResponseEntity.ok(publicacaoAtualizadaSalva);
			} else {
				return ResponseEntity.notFound().build(); // Retorna 404 Not Found se a publicacao não for encontrada
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error em caso de exceção
		}
	}
}
