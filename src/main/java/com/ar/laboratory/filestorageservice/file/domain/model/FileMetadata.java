package com.ar.laboratory.filestorageservice.file.domain.model;

import com.ar.laboratory.filestorageservice.file.domain.exception.InvalidFileTransitionException;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Metadatos de un archivo. El binario vive en el object store; aquí solo se guardan el estado y las
 * claves de almacenamiento (original y miniatura derivada).
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadata {

    private UUID id;
    private String filename;
    private String contentType;
    private String storageKey;
    private String thumbnailKey;
    private long sizeBytes;
    private FileStatus status;
    private UUID ownerId;
    private String lastError;
    private Instant createdAt;
    private Instant updatedAt;

    public static FileMetadata pending(
            String filename, String contentType, String storageKey, UUID ownerId, Instant now) {
        return FileMetadata.builder()
                .id(UUID.randomUUID())
                .filename(filename)
                .contentType(contentType)
                .storageKey(storageKey)
                .ownerId(ownerId)
                .status(FileStatus.PENDING)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    /** PENDING → UPLOADED cuando el cliente confirma la subida directa al store. */
    public void markUploaded(long sizeBytes, Instant now) {
        require(FileStatus.PENDING);
        this.sizeBytes = sizeBytes;
        this.status = FileStatus.UPLOADED;
        this.updatedAt = now;
    }

    /** UPLOADED → PROCESSING al iniciar la generación de variantes. */
    public void markProcessing(Instant now) {
        require(FileStatus.UPLOADED);
        this.status = FileStatus.PROCESSING;
        this.updatedAt = now;
    }

    /** PROCESSING → READY con la miniatura generada. */
    public void markReady(String thumbnailKey, Instant now) {
        require(FileStatus.PROCESSING);
        this.thumbnailKey = thumbnailKey;
        this.status = FileStatus.READY;
        this.updatedAt = now;
    }

    public void markFailed(String reason, Instant now) {
        this.status = FileStatus.FAILED;
        this.lastError = reason;
        this.updatedAt = now;
    }

    private void require(FileStatus expected) {
        if (this.status != expected) {
            throw new InvalidFileTransitionException(this.status, expected);
        }
    }
}
