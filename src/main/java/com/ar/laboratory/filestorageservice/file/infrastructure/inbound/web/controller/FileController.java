package com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.controller;

import com.ar.laboratory.filestorageservice.file.application.inbound.command.ConfirmUploadCommand;
import com.ar.laboratory.filestorageservice.file.application.inbound.command.GetFileCommand;
import com.ar.laboratory.filestorageservice.file.application.inbound.command.RequestUploadCommand;
import com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.dto.CompleteUploadRequest;
import com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.dto.FileResponse;
import com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.dto.RequestUploadRequest;
import com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.dto.UploadTicketResponse;
import com.ar.laboratory.filestorageservice.file.infrastructure.inbound.web.mapper.FileDtoMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** API de archivos: presigned URLs, confirmación de subida y consulta. */
@Tag(name = "Files", description = "Subida directa con presigned URLs y post-proceso")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@RateLimiter(name = "files-api")
public class FileController {

    private final RequestUploadCommand requestUploadCommand;
    private final ConfirmUploadCommand confirmUploadCommand;
    private final GetFileCommand getFileCommand;
    private final FileDtoMapper mapper;

    @PostMapping
    public ResponseEntity<UploadTicketResponse> requestUpload(
            @RequestHeader(value = "X-User-Id", required = false) UUID ownerId,
            @Valid @RequestBody RequestUploadRequest request) {
        var ticket =
                requestUploadCommand.execute(
                        request.getFilename(), request.getContentType(), ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toTicketResponse(ticket));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<FileResponse> complete(
            @PathVariable UUID id, @Valid @RequestBody CompleteUploadRequest request) {
        confirmUploadCommand.execute(id, request.getSizeBytes());
        return ResponseEntity.ok(mapper.toFileResponse(getFileCommand.execute(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toFileResponse(getFileCommand.execute(id)));
    }
}
