package com.ar.laboratory.filestorageservice.file.application.inbound.command;

import com.ar.laboratory.filestorageservice.file.application.model.FileView;
import java.util.UUID;

/** Puerto de entrada: consultar un archivo con sus URLs. */
public interface GetFileCommand {
    FileView execute(UUID fileId);
}
