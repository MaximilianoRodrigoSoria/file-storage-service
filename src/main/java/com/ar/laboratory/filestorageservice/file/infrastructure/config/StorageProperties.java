package com.ar.laboratory.filestorageservice.file.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** Configuración de almacenamiento ({@code app.storage.*}). */
@Data
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {
    /** Base pública del object store (S3/MinIO/CDN) para construir URLs prefirmadas. */
    private String baseUrl = "http://localhost:9000/object-store";
    /** Vigencia (segundos) de las URLs prefirmadas. */
    private long presignExpirySeconds = 900;
}
