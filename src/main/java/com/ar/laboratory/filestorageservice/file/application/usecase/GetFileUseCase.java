package com.ar.laboratory.filestorageservice.file.application.usecase;

import com.ar.laboratory.filestorageservice.file.application.inbound.command.GetFileCommand;
import com.ar.laboratory.filestorageservice.file.application.model.FileView;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.FileRepositoryPort;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.StoragePort;
import com.ar.laboratory.filestorageservice.file.domain.exception.FileNotFoundException;
import com.ar.laboratory.filestorageservice.file.domain.model.FileMetadata;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/** Devuelve el archivo con sus URLs de descarga prefirmadas. POJO sin framework. */
@RequiredArgsConstructor
public class GetFileUseCase implements GetFileCommand {

    private final FileRepositoryPort files;
    private final StoragePort storage;

    @Override
    public FileView execute(UUID fileId) {
        FileMetadata file =
                files.findById(fileId).orElseThrow(() -> new FileNotFoundException(fileId));
        String downloadUrl = storage.presignedDownload(file.getStorageKey());
        String thumbnailUrl =
                file.getThumbnailKey() == null
                        ? null
                        : storage.presignedDownload(file.getThumbnailKey());
        return new FileView(file, downloadUrl, thumbnailUrl);
    }
}
