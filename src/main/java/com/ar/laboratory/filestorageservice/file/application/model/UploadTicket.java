package com.ar.laboratory.filestorageservice.file.application.model;

import java.util.UUID;

/** Ticket de subida: id del archivo, clave de almacenamiento y URL prefirmada de PUT. */
public record UploadTicket(UUID fileId, String storageKey, String uploadUrl) {}
