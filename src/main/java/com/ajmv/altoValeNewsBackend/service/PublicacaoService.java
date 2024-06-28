package com.ajmv.altoValeNewsBackend.service;

import com.ajmv.altoValeNewsBackend.model.Comentario;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ajmv.altoValeNewsBackend.model.Publicacao;
import com.ajmv.altoValeNewsBackend.model.MediaFile;
import com.ajmv.altoValeNewsBackend.model.Editor;
import com.ajmv.altoValeNewsBackend.repository.PublicacaoRepository;
import com.ajmv.altoValeNewsBackend.repository.EditorRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/*
     Devido ao JPA/Hibernate ter problemas ao recuperar arquivos de mídia,
     usamos o JDBC para recuperar a publicação manualmente,
     recuperando seus valores e chaves que são usadas para recuperar editor,
     mídia e comentários através de seus respectivos services.

     Fortemente recomendado usar JDBC ao invés do JpaRepository para qualquer método que lide com byte[]/bytea no db.
*/

@Service
public class PublicacaoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    @Autowired
    private EditorRepository editorRepository;

    @Autowired
    private MediaFileService fileService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private EditorService editorService;

    private static final Logger LOGGER = Logger.getLogger(PublicacaoService.class.getName());

    public List<Publicacao> getAll() throws SQLException {
        String sql = "SELECT p.publicacao_id, p.editor_id, p.titulo, p.data, p.texto, p.categoria, " +
                "p.visibilidade_vip, p.curtidas, p.imagem_id, p.video_id " +
                "FROM publicacao p " +
                "order by p.publicacao_id desc";

        List<Publicacao> publicacoes = new ArrayList<Publicacao>();

        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Publicacao publicacao = new Publicacao();
                publicacao.setPublicacaoId(rs.getInt("publicacao_id"));
                publicacao.setTitulo(rs.getString("titulo"));
                publicacao.setData(rs.getObject("data", LocalDate.class));
                publicacao.setTexto(rs.getString("texto"));
                publicacao.setCategoria(rs.getString("categoria"));
                publicacao.setVisibilidadeVip(rs.getBoolean("visibilidade_vip"));
                publicacao.setCurtidas(rs.getInt("curtidas"));

                Integer editorId = rs.getInt("editor_id");
                if (!rs.wasNull()) {
                    Editor editor = editorService.getEditor(editorId);
                    publicacao.setEditor(editor);
                }

                Long imagemId = rs.getLong("imagem_id");
                if (!rs.wasNull()) {
                    MediaFile imagem = fileService.getFile(imagemId);
                    publicacao.setImagem(imagem);
                }

                Long videoId = rs.getLong("video_id");
                if (!rs.wasNull()) {
                    MediaFile video = fileService.getFile(videoId);
                    publicacao.setVideo(video);
                }

                List<Comentario> comentarios = comentarioService.getComentariosByPublicacao_id(publicacao.getPublicacaoId());
                publicacao.setComentarios(comentarios);

                publicacoes.add(publicacao);
            }
        }

        return publicacoes;
    }

    public Publicacao getPublicacao(Integer publicacaoId) throws SQLException {
        String sql = "SELECT p.publicacao_id, p.editor_id, p.titulo, p.data, p.texto, p.categoria, " +
                "p.visibilidade_vip, p.curtidas, p.imagem_id, p.video_id " +
                "FROM publicacao p " +
                "WHERE p.publicacao_id = ?";

        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, publicacaoId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Publicacao publicacao = new Publicacao();
                    publicacao.setPublicacaoId(rs.getInt("publicacao_id"));
                    publicacao.setTitulo(rs.getString("titulo"));
                    publicacao.setData(rs.getObject("data", LocalDate.class));
                    publicacao.setTexto(rs.getString("texto"));
                    publicacao.setCategoria(rs.getString("categoria"));
                    publicacao.setVisibilidadeVip(rs.getBoolean("visibilidade_vip"));
                    publicacao.setCurtidas(rs.getInt("curtidas"));

                    Integer editorId = rs.getInt("editor_id");
                    if (editorId != null) {
                        Editor editor = editorService.getEditor(editorId);
                        publicacao.setEditor(editor);
                    }

                    Long imagemId = rs.getLong("imagem_id");
                    if (imagemId != null) {
                        MediaFile imagem = fileService.getFile(imagemId);
                        publicacao.setImagem(imagem);
                    }

                    Long videoId = rs.getLong("video_id");
                    if (videoId != null) {
                        MediaFile video = fileService.getFile(videoId);
                        publicacao.setVideo(video);
                    }

                    List<Comentario> comentarios = comentarioService.getComentariosByPublicacao_id(publicacao.getPublicacaoId());
                    publicacao.setComentarios(comentarios);

                    return publicacao;
                } else {
                    return null;
                }
            }
        }
    }

    /* Ao inserir publicações com mídia o problema anterior não acontece diretamente,
    mas sim no MediaFileService (usamos JDBC lá). */
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

    public Publicacao updatePublicacao(Integer id, JSONObject publicacaoJson,
                                       MultipartFile imageFile, MultipartFile videoFile) throws IOException, SQLException {
        Publicacao publicacao = publicacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Publicação não encontrada: " + id));

        Integer editorId = Optional.ofNullable(publicacaoJson.optJSONObject("editor"))
                .map(editorObj -> editorObj.getInt("userId"))
                .orElseThrow(() -> new IllegalArgumentException("Campo 'editor' é obrigatório"));
        String titulo = publicacaoJson.optString("titulo", null);
        LocalDate data = LocalDate.parse(publicacaoJson.optString("data", null));
        String texto = publicacaoJson.optString("texto", null);
        String categoria = publicacaoJson.optString("categoria", null);
        Boolean visibilidadeVip = publicacaoJson.optBoolean("visibilidadeVip", false);
        Integer curtidas = publicacaoJson.optInt("curtidas", 0);

        Editor editor = null;
        if (editorId != null) {
            editor = editorRepository.findById(editorId)
                    .orElseThrow(() -> new IllegalArgumentException("Editor não encontrado: " + editorId));
        }
        publicacao.setEditor(editor);
        publicacao.setTitulo(titulo);
        publicacao.setData(data);
        publicacao.setTexto(texto);
        publicacao.setCategoria(categoria);
        publicacao.setVisibilidadeVip(visibilidadeVip);
        publicacao.setCurtidas(curtidas);

        // Apesar de ser um PUT, executamos o serviço de arquivos somente se houver algo a ser salvo
        MediaFile imagem = null;
        if (imageFile != null) {
            imagem = fileService.saveFile(imageFile);
        }
        publicacao.setImagem(imagem);

        MediaFile video = null;
        if (videoFile != null) {
            video = fileService.saveFile(videoFile);
        }
        publicacao.setVideo(video);

        return publicacaoRepository.save(publicacao);
    }

    public Publicacao partialUpdatePublicacao(Integer id, Integer editorId, String titulo, LocalDate data, String texto, MultipartFile imageFile, MultipartFile videoFile,
                                              String categoria, Boolean visibilidadeVip, Integer curtidas) throws IOException, SQLException {
        Publicacao publicacao = publicacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Publicação não encontrada: " + id));

        if (editorId != null) {
            Editor editor = editorRepository.findById(editorId)
                    .orElseThrow(() -> new IllegalArgumentException("Editor não encontrado: " + editorId));
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

    public Publicacao likePublicacao(Integer id) throws SQLException {
        String sql = "UPDATE publicacao SET curtidas = curtidas + 1 WHERE publicacao_id = ?";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                return ps;
            }
        });

        return getPublicacao(id);
    }

    public Publicacao dislikePublicacao(Integer id) throws SQLException {
        String sql = "UPDATE publicacao SET curtidas = curtidas - 1 WHERE publicacao_id = ?";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                return ps;
            }
        });

        return getPublicacao(id);
    }
}
