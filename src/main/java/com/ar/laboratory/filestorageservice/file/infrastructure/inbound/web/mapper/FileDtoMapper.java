package com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.mapper;

import com.ar.laboratory.filestorageservice.file.application.model.FileView;
import com.ar.laboratory.filestorageservice.file.application.model.UploadTicket;
import com.ar.laboratory.filestorageservice.file.domain.model.FileMetadata;
import com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.dto.FileResponse;
import com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.dto.UploadTicketResponse;
import org.springframework.stereotype.Component;

/** Conversión a DTOs de la API de archivos. */
@Component
public class FileDtoMapper {

    public UploadTicketResponse toTicketResponse(UploadTicket ticket) {
        return UploadTicketResponse.builder()
                .fileId(ticket.fileId())
                .storageKey(ticket.storageKey())
                .uploadUrl(ticket.uploadUrl())
                .build();
    }

    public FileResponse toFileResponse(FileView view) {
        FileMetadata m = view.metadata();
        return FileResponse.builder()
                .id(m.getId())
                .filename(m.getFilename())
                .contentType(m.getContentType())
                .status(m.getStatus() == null ? null : m.getStatus().name())
                .sizeBytes(m.getSizeBytes())
                .downloadUrl(view.downloadUrl())
                .thumbnailUrl(view.thumbnailUrl())
                .createdAt(m.getCreatedAt())
                .build();
    }
}
