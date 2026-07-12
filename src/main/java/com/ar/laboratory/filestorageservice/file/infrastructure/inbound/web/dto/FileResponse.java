package com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Vista de un archivo. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileResponse {
    private UUID id;
    private String filename;
    private String contentType;
    private String status;
    private long sizeBytes;
    private String downloadUrl;
    private String thumbnailUrl;
    private Instant createdAt;
}
