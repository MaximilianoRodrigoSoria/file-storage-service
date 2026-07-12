package com.ar.laboratory.filestorageservice.file.domain.exception;

import java.util.UUID;

/** No se encontró el archivo. */
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(UUID id) {
        super("Archivo no encontrado: " + id);
    }
}
