package com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Solicitud de URL de subida. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUploadRequest {

    @NotBlank(message = "El filename es obligatorio")
    @Size(max = 300)
    private String filename;

    @Size(max = 150)
    private String contentType;
}
