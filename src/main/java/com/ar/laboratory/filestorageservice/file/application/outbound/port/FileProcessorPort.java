package com.ar.laboratory.filestorageservice.file.application.outbound.port;

/** Puerto de salida para el post-proceso del archivo (genera la miniatura). */
public interface FileProcessorPort {
    /** Genera una variante (miniatura) del objeto y devuelve su clave de almacenamiento. */
    String generateThumbnail(String storageKey);
}
