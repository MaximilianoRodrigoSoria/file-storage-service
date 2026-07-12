package com.ar.laboratory.filestorageservice.file.application.inbound.command;

import com.ar.laboratory.filestorageservice.file.application.model.UploadTicket;
import java.util.UUID;

/** Puerto de entrada: solicitar una URL de subida prefirmada. */
public interface RequestUploadCommand {
    UploadTicket execute(String filename, String contentType, UUID ownerId);
}
