package com.ajmv.altoValeNewsBackend.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ajmv.altoValeNewsBackend.model.Publicacao;
import com.ajmv.altoValeNewsBackend.model.MediaFile;
import com.ajmv.altoValeNewsBackend.model.Editor;
import com.ajmv.altoValeNewsBackend.repository.PublicacaoRepository;
import com.ajmv.altoValeNewsBackend.repository.EditorRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@Service
public class PublicacaoService {

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    @Autowired
    private EditorRepository editorRepository;

    @Autowired
    private MediaFileService fileService;

    public Publicacao getPublicacao(Integer id) {
        return publicacaoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Publicacao savePublicacao(Integer editorId, String titulo, LocalDate data, String texto,
                                     MultipartFile imageFile, MultipartFile videoFile,
                                     String categoria, Boolean visibilidadeVip, Integer curtidas) throws IOException, SQLException {
        Editor editor = editorRepository.findById(editorId)
                .orElseThrow(() -> new IllegalArgumentException("Editor não encontrado: " + editorId));

        MediaFile imagem = processMediaFile(imageFile);
        MediaFile video = processMediaFile(videoFile);

        Publicacao publicacao = new Publicacao();
        publicacao.setEditor(editor);
        publicacao.setTitulo(titulo);
        publicacao.setData(data);
        publicacao.setTexto(texto);
        publicacao.setImagem(imagem);
        publicacao.setVideo(video);
        publicacao.setCategoria(categoria);
        publicacao.setVisibilidadeVip(visibilidadeVip);
        publicacao.setCurtidas(curtidas);

        return publicacaoRepository.save(publicacao);
    }

    private MediaFile processMediaFile(MultipartFile file) throws IOException, SQLException {
        if (file != null && !file.isEmpty()) {
            return fileService.saveFile(file);
        }
        return null;
    }

    public Publicacao updatePublicacao(Integer id, Integer editorId, String titulo, LocalDate data, String texto, MultipartFile imageFile, MultipartFile videoFile,
                                       String categoria, Boolean visibilidadeVip, Integer curtidas) throws IOException, SQLException {
        Publicacao publicacao = publicacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Publicação não encontrada: " + id));

        if (editorId != null) {
            Editor editor = editorRepository.findById(editorId).orElseThrow(() -> new IllegalArgumentException("Editor não encontrado: " + editorId));
            publicacao.setEditor(editor);
        }

        publicacao.setTitulo(titulo);
        publicacao.setData(data);
        publicacao.setTexto(texto);
        publicacao.setCategoria(categoria);
        publicacao.setVisibilidadeVip(visibilidadeVip);
        publicacao.setCurtidas(curtidas);

        if (imageFile != null) {
            MediaFile imagem = fileService.saveFile(imageFile);
            publicacao.setImagem(imagem);
        }

        if (videoFile != null) {
            MediaFile video = fileService.saveFile(videoFile);
            publicacao.setVideo(video);
        }

        return publicacaoRepository.save(publicacao);
    }

    public Publicacao partialUpdatePublicacao(Integer id, Integer editorId, String titulo, LocalDate data, String texto, MultipartFile imageFile, MultipartFile videoFile,
                                              String categoria, Boolean visibilidadeVip, Integer curtidas) throws IOException, SQLException {
        Publicacao publicacao = publicacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Publicação não encontrada: " + id));

        if (editorId != null) {
            Editor editor = editorRepository.findById(editorId).orElseThrow(() -> new IllegalArgumentException("Editor não encontrado: " + editorId));
            publicacao.setEditor(editor);
        }

        if (titulo != null) {
            publicacao.setTitulo(titulo);
        }
        if (data != null) {
            publicacao.setData(data);
        }
        if (texto != null) {
            publicacao.setTexto(texto);
        }
        if (categoria != null) {
            publicacao.setCategoria(categoria);
        }
        if (visibilidadeVip != null) {
            publicacao.setVisibilidadeVip(visibilidadeVip);
        }
        if (curtidas != null) {
            publicacao.setCurtidas(curtidas);
        }

        if (imageFile != null) {
            MediaFile imagem = fileService.saveFile(imageFile);
            publicacao.setImagem(imagem);
        }

        if (videoFile != null) {
            MediaFile video = fileService.saveFile(videoFile);
            publicacao.setVideo(video);
        }

        return publicacaoRepository.save(publicacao);
    }

    public Publicacao likePublicacao(Integer id) {
        Publicacao publicacao = publicacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Publicação não encontrada: " + id));
        publicacao.setCurtidas(publicacao.getCurtidas() + 1);
        return publicacaoRepository.save(publicacao);
    }

    public Publicacao dislikePublicacao(Integer id) {
        Publicacao publicacao = publicacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Publicação não encontrada: " + id));
        publicacao.setCurtidas(publicacao.getCurtidas() - 1);
        return publicacaoRepository.save(publicacao);
    }


}
