package com.ar.laboratory.filestorageservice.file.infrastructure.outbound.storage;

import com.ar.laboratory.filestorageservice.file.application.outbound.port.StoragePort;
import com.ar.laboratory.filestorageservice.file.infrastructure.config.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Adaptador de object store para el núcleo: simula URLs prefirmadas. En un despliegue real usa el
 * SDK de S3/MinIO para firmar PUT/GET reales.
 */
@Component
@RequiredArgsConstructor
public class StubStorageAdapter implements StoragePort {

    private final StorageProperties properties;

    @Override
    public String presignedUpload(String storageKey) {
        return url(storageKey, "PUT");
    }

    @Override
    public String presignedDownload(String storageKey) {
        return url(storageKey, "GET");
    }

    private String url(String storageKey, String method) {
        return properties.getBaseUrl()
                + "/"
                + storageKey
                + "?method="
                + method
                + "&expires="
                + properties.getPresignExpirySeconds()
                + "&sig="
                + Integer.toHexString((storageKey + method).hashCode());
    }
}
