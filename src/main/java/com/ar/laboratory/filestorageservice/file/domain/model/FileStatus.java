package com.ar.laboratory.filestorageservice.file.domain.model;

/** Ciclo de vida de un archivo. */
public enum FileStatus {
    PENDING,
    UPLOADED,
    PROCESSING,
    READY,
    FAILED
}
