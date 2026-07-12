package com.ar.laboratory.filestorageservice.file.application.outbound.port;

/** Puerto de salida hacia el object store (S3-compatible): genera URLs prefirmadas. */
public interface StoragePort {
    String presignedUpload(String storageKey);

    String presignedDownload(String storageKey);
}
