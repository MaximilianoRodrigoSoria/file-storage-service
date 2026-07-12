package com.ar.laboratory.filestorageservice.file.application.model;

import com.ar.laboratory.filestorageservice.file.domain.model.FileMetadata;

/** Vista de un archivo con sus URLs de descarga prefirmadas. */
public record FileView(FileMetadata metadata, String downloadUrl, String thumbnailUrl) {}
