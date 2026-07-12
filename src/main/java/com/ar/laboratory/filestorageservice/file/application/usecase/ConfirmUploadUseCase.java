package com.ar.laboratory.filestorageservice.file.application.usecase;

import com.ar.laboratory.filestorageservice.file.application.inbound.command.ConfirmUploadCommand;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.FileProcessorPort;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.FileRepositoryPort;
import com.ar.laboratory.filestorageservice.file.domain.exception.FileNotFoundException;
import com.ar.laboratory.filestorageservice.file.domain.model.FileMetadata;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Confirma la subida y ejecuta el post-proceso (generación de miniatura). En un despliegue real el
 * post-proceso se dispara por un evento y lo atiende un worker; aquí el núcleo lo hace en línea.
 */
@Slf4j
@RequiredArgsConstructor
public class ConfirmUploadUseCase implements ConfirmUploadCommand {

    private final FileRepositoryPort files;
    private final FileProcessorPort processor;

    @Override
    public FileMetadata execute(UUID fileId, long sizeBytes) {
        Instant now = Instant.now();
        FileMetadata file =
                files.findById(fileId).orElseThrow(() -> new FileNotFoundException(fileId));
        file.markUploaded(sizeBytes, now);
        file.markProcessing(now);
        try {
            String thumbnailKey = processor.generateThumbnail(file.getStorageKey());
            file.markReady(thumbnailKey, Instant.now());
        } catch (Exception e) {
            file.markFailed("Fallo en el post-proceso: " + e.getMessage(), Instant.now());
            log.error("Post-proceso falló para {}", fileId, e);
        }
        return files.save(file);
    }
}
