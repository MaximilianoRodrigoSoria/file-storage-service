package com.ar.laboratory.filestorageservice.file.infrastructure.outbound.persistence.adapter;

import com.ar.laboratory.filestorageservice.file.application.outbound.port.FileRepositoryPort;
import com.ar.laboratory.filestorageservice.file.domain.model.FileMetadata;
import com.ar.laboratory.filestorageservice.file.infrastructure.outbound.persistence.mapper.FileMetadataEntityMapper;
import com.ar.laboratory.filestorageservice.file.infrastructure.outbound.persistence.repository.FileMetadataJpaRepository;
import com.ar.laboratory.filestorageservice.shared.infrastructure.exception.InfrastructureException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Adaptador de persistencia de metadatos de archivo. */
@Component
@RequiredArgsConstructor
public class FilePersistenceAdapter implements FileRepositoryPort {

    private final FileMetadataJpaRepository repository;
    private final FileMetadataEntityMapper mapper;

    @Override
    public FileMetadata save(FileMetadata file) {
        try {
            return mapper.toDomain(repository.save(mapper.toEntity(file)));
        } catch (Exception e) {
            throw new InfrastructureException("Error guardando archivo", e);
        }
    }

    @Override
    public Optional<FileMetadata> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
