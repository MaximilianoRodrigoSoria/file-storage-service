package com.ar.laboratory.filestorageservice.file.infrastructure.outbound.persistence.mapper;

import com.ar.laboratory.filestorageservice.file.domain.model.FileMetadata;
import com.ar.laboratory.filestorageservice.file.domain.model.FileStatus;
import com.ar.laboratory.filestorageservice.file.infrastructure.outbound.persistence.entity.FileMetadataEntity;
import org.springframework.stereotype.Component;

/** Conversión FileMetadataEntity ↔ FileMetadata. */
@Component
public class FileMetadataEntityMapper {

    public FileMetadata toDomain(FileMetadataEntity e) {
        if (e == null) {
            return null;
        }
        return FileMetadata.builder()
                .id(e.getId())
                .filename(e.getFilename())
                .contentType(e.getContentType())
                .storageKey(e.getStorageKey())
                .thumbnailKey(e.getThumbnailKey())
                .sizeBytes(e.getSizeBytes())
                .status(e.getStatus() == null ? null : FileStatus.valueOf(e.getStatus()))
                .ownerId(e.getOwnerId())
                .lastError(e.getLastError())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    public FileMetadataEntity toEntity(FileMetadata f) {
        return FileMetadataEntity.builder()
                .id(f.getId())
                .filename(f.getFilename())
                .contentType(f.getContentType())
                .storageKey(f.getStorageKey())
                .thumbnailKey(f.getThumbnailKey())
                .sizeBytes(f.getSizeBytes())
                .status(f.getStatus() == null ? null : f.getStatus().name())
                .ownerId(f.getOwnerId())
                .lastError(f.getLastError())
                .createdAt(f.getCreatedAt())
                .updatedAt(f.getUpdatedAt())
                .build();
    }
}
