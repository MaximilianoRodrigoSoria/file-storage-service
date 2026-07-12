package com.ar.laboratory.filestorageservice.file.infrastructure.outbound.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/** Entidad JPA de metadatos de archivo (tabla {@code app.files}). */
@Entity
@Table(name = "files", schema = "app")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadataEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "filename", nullable = false, length = 300)
    private String filename;

    @Column(name = "content_type", length = 150)
    private String contentType;

    @Column(name = "storage_key", nullable = false, length = 500)
    private String storageKey;

    @Column(name = "thumbnail_key", length = 500)
    private String thumbnailKey;

    @Column(name = "size_bytes", nullable = false)
    private long sizeBytes;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name = "last_error", columnDefinition = "text")
    private String lastError;

    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
