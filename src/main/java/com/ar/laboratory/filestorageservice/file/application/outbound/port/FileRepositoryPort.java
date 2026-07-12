package com.ar.laboratory.filestorageservice.file.application.outbound.port;

import com.ar.laboratory.filestorageservice.file.domain.model.FileMetadata;
import java.util.Optional;
import java.util.UUID;

/** Puerto de salida para la persistencia de metadatos de archivo. */
public interface FileRepositoryPort {
    FileMetadata save(FileMetadata file);

    Optional<FileMetadata> findById(UUID id);
}
