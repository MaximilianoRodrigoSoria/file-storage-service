package com.ar.laboratory.filestorageservice.file.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.ar.laboratory.filestorageservice.file.application.model.FileView;
import com.ar.laboratory.filestorageservice.file.application.model.UploadTicket;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.FileProcessorPort;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.FileRepositoryPort;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.StoragePort;
import com.ar.laboratory.filestorageservice.file.domain.exception.FileNotFoundException;
import com.ar.laboratory.filestorageservice.file.domain.model.FileMetadata;
import com.ar.laboratory.filestorageservice.file.domain.model.FileStatus;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Casos de uso de archivos")
class FileUseCasesTest {

    @Mock private FileRepositoryPort files;
    @Mock private StoragePort storage;
    @Mock private FileProcessorPort processor;

    @Test
    @DisplayName("request upload crea PENDING y devuelve la URL prefirmada")
    void requestUpload() {
        when(files.save(any(FileMetadata.class))).thenAnswer(inv -> inv.getArgument(0));
        when(storage.presignedUpload(anyString())).thenReturn("http://store/put");

        UploadTicket ticket =
                new RequestUploadUseCase(files, storage)
                        .execute("a.png", "image/png", UUID.randomUUID());

        assertThat(ticket.uploadUrl()).isEqualTo("http://store/put");
        assertThat(ticket.fileId()).isNotNull();
    }

    @Test
    @DisplayName("confirm marca READY con la miniatura generada")
    void confirm() {
        UUID id = UUID.randomUUID();
        FileMetadata pending =
                FileMetadata.pending("a.png", "image/png", "k", null, Instant.now());
        when(files.findById(id)).thenReturn(Optional.of(pending));
        when(processor.generateThumbnail("k")).thenReturn("thumb-k");
        when(files.save(any(FileMetadata.class))).thenAnswer(inv -> inv.getArgument(0));

        FileMetadata result = new ConfirmUploadUseCase(files, processor).execute(id, 2048);

        assertThat(result.getStatus()).isEqualTo(FileStatus.READY);
        assertThat(result.getThumbnailKey()).isEqualTo("thumb-k");
        assertThat(result.getSizeBytes()).isEqualTo(2048);
    }

    @Test
    @DisplayName("confirm de archivo inexistente → FileNotFound")
    void confirmMissing() {
        UUID id = UUID.randomUUID();
        when(files.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> new ConfirmUploadUseCase(files, processor).execute(id, 1))
                .isInstanceOf(FileNotFoundException.class);
    }

    @Test
    @DisplayName("get devuelve la vista con URL de descarga")
    void get() {
        UUID id = UUID.randomUUID();
        FileMetadata ready =
                FileMetadata.builder()
                        .id(id)
                        .storageKey("k")
                        .thumbnailKey("t")
                        .status(FileStatus.READY)
                        .build();
        when(files.findById(id)).thenReturn(Optional.of(ready));
        when(storage.presignedDownload("k")).thenReturn("http://store/k");
        when(storage.presignedDownload("t")).thenReturn("http://store/t");

        FileView view = new GetFileUseCase(files, storage).execute(id);
        assertThat(view.downloadUrl()).isEqualTo("http://store/k");
        assertThat(view.thumbnailUrl()).isEqualTo("http://store/t");
    }
}
