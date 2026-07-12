package com.ar.laboratory.filestorageservice.file.infrastructure.config;

import com.ar.laboratory.filestorageservice.file.application.inbound.command.ConfirmUploadCommand;
import com.ar.laboratory.filestorageservice.file.application.inbound.command.GetFileCommand;
import com.ar.laboratory.filestorageservice.file.application.inbound.command.RequestUploadCommand;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.FileProcessorPort;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.FileRepositoryPort;
import com.ar.laboratory.filestorageservice.file.application.outbound.port.StoragePort;
import com.ar.laboratory.filestorageservice.file.application.usecase.ConfirmUploadUseCase;
import com.ar.laboratory.filestorageservice.file.application.usecase.GetFileUseCase;
import com.ar.laboratory.filestorageservice.file.application.usecase.RequestUploadUseCase;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Wiring de los casos de uso de almacenamiento. */
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class FileConfig {

    @Bean
    public RequestUploadCommand requestUploadCommand(FileRepositoryPort files, StoragePort storage) {
        return new RequestUploadUseCase(files, storage);
    }

    @Bean
    public ConfirmUploadCommand confirmUploadCommand(
            FileRepositoryPort files, FileProcessorPort processor) {
        return new ConfirmUploadUseCase(files, processor);
    }

    @Bean
    public GetFileCommand getFileCommand(FileRepositoryPort files, StoragePort storage) {
        return new GetFileUseCase(files, storage);
    }
}
