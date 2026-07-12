package com.ar.laboratory.filestorageservice.file.application.inbound.command;

import com.ar.laboratory.filestorageservice.file.domain.model.FileMetadata;
import java.util.UUID;

/** Puerto de entrada: confirmar la subida y disparar el post-proceso. */
public interface ConfirmUploadCommand {
    FileMetadata execute(UUID fileId, long sizeBytes);
}
