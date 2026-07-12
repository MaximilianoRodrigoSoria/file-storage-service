package com.ar.laboratory.filestorageservice.file.application.usecase;

import com.ar.laboratory.filestorageservice.file.application.inbound.command.RequestUploadCommand;
import com.ar.laboratory.filestorageservice.file.application.model.UploadTicket;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.FileRepositoryPort;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.StoragePort;
import com.ar.laboratory.filestorageservice.file.domain.model.FileMetadata;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Registra el archivo como PENDING y devuelve una URL de subida prefirmada. POJO sin framework. */
@Slf4j
@RequiredArgsConstructor
public class RequestUploadUseCase implements RequestUploadCommand {

    private final FileRepositoryPort files;
    private final StoragePort storage;

    @Override
    public UploadTicket execute(String filename, String contentType, UUID ownerId) {
        Instant now = Instant.now();
        String storageKey = "uploads/" + UUID.randomUUID() + "/" + filename;
        FileMetadata file =
                files.save(FileMetadata.pending(filename, contentType, storageKey, ownerId, now));
        String uploadUrl = storage.presignedUpload(storageKey);
        log.info("Upload solicitado file={} key={}", file.getId(), storageKey);
        return new UploadTicket(file.getId(), storageKey, uploadUrl);
    }
}
