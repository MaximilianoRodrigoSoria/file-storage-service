package com.ar.laboratory.filestorageservice.file.infrastructure.outbound.persistence.repository;

import com.ar.laboratory.filestorageservice.file.infrastructure.outbound.persistence.entity.FileMetadataEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio JPA de metadatos de archivo. */
public interface FileMetadataJpaRepository extends JpaRepository<FileMetadataEntity, UUID> {}
