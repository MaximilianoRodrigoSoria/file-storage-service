package com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Respuesta con la URL de subida prefirmada. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadTicketResponse {
    private UUID fileId;
    private String storageKey;
    private String uploadUrl;
}
