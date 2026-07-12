package com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Confirmación de subida. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteUploadRequest {

    @PositiveOrZero(message = "El tamaño no puede ser negativo")
    private long sizeBytes;
}
