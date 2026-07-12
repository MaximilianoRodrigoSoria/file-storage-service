package com.ar.laboratory.filestorageservice.file.domain.exception;

import com.ar.laboratory.filestorageservice.file.domain.model.FileStatus;

/** Transición de estado de archivo inválida. */
public class InvalidFileTransitionException extends RuntimeException {
    public InvalidFileTransitionException(FileStatus from, FileStatus expected) {
        super("Estado inválido: " + from + " (se esperaba " + expected + ")");
    }
}
