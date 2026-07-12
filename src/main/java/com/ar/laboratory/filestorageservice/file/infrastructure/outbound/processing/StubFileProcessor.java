package com.ar.laboratory.filestorageservice.file.infrastructure.outbound.processing;

import com.ar.laboratory.filestorageservice.file.application.outbound.port.FileProcessorPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Procesador de ejemplo: genera la clave de una miniatura. En un despliegue real descargaría el
 * objeto, generaría variantes (resize/transcode) y las subiría al store.
 */
@Slf4j
@Component
public class StubFileProcessor implements FileProcessorPort {

    @Override
    public String generateThumbnail(String storageKey) {
        String thumbKey = "thumbnails/" + storageKey + ".thumb.jpg";
        log.info("[processor] miniatura generada para {} -> {}", storageKey, thumbKey);
        return thumbKey;
    }
}
