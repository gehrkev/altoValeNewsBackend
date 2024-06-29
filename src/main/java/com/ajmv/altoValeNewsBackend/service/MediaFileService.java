package com.ajmv.altoValeNewsBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.ajmv.altoValeNewsBackend.model.MediaFile;
import com.ajmv.altoValeNewsBackend.repository.MediaFileRepository;

import java.io.IOException;
import java.sql.*;

@Service
public class MediaFileService {

    @Autowired
    private MediaFileRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = {SQLException.class, IOException.class})
    public MediaFile saveFile(MultipartFile file) throws IOException, SQLException {
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileName(file.getOriginalFilename());
        mediaFile.setFileType(file.getContentType());
        mediaFile.setData(file.getBytes());

        String sql = "INSERT INTO media_file (data, file_name, file_type) VALUES (?, ?, ?)";
        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setBytes(1, mediaFile.getData());
            pstmt.setString(2, mediaFile.getFileName());
            pstmt.setString(3, mediaFile.getFileType());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert file, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mediaFile.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Failed to retrieve auto-generated key.");
                }
            }
        }

        return mediaFile;
    }

    public MediaFile getFile(Long id) throws SQLException {
        String sql = "SELECT id, file_name, file_type, data FROM media_file WHERE id = ?";

        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    MediaFile mediaFile = new MediaFile();
                    mediaFile.setId(rs.getLong("id"));
                    mediaFile.setFileName(rs.getString("file_name"));
                    mediaFile.setFileType(rs.getString("file_type"));
                    mediaFile.setData(rs.getBytes("data"));
                    return mediaFile;
                } else {
                    return null;
                }
            }
        }
    }
}