package com.ar.laboratory.filestorageservice.file.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ar.laboratory.filestorageservice.file.domain.exception.InvalidFileTransitionException;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Dominio de archivos")
class FileDomainTest {

    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");

    @Test
    @DisplayName("PENDING → UPLOADED → PROCESSING → READY")
    void lifecycle() {
        FileMetadata f = FileMetadata.pending("a.png", "image/png", "k", UUID.randomUUID(), NOW);
        assertThat(f.getStatus()).isEqualTo(FileStatus.PENDING);
        f.markUploaded(100, NOW);
        assertThat(f.getStatus()).isEqualTo(FileStatus.UPLOADED);
        assertThat(f.getSizeBytes()).isEqualTo(100);
        f.markProcessing(NOW);
        f.markReady("thumb", NOW);
        assertThat(f.getStatus()).isEqualTo(FileStatus.READY);
        assertThat(f.getThumbnailKey()).isEqualTo("thumb");
    }

    @Test
    @DisplayName("markReady sin pasar por PROCESSING → inválido")
    void invalid() {
        FileMetadata f = FileMetadata.pending("a.png", "image/png", "k", null, NOW);
        assertThatThrownBy(() -> f.markReady("t", NOW))
                .isInstanceOf(InvalidFileTransitionException.class);
    }
}
