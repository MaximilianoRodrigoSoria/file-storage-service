package com.ar.laboratory.filestorageservice.example.application.inbound.command;

import com.ar.laboratory.filestorageservice.example.domain.model.Example;

/** Puerto de entrada para crear un Example */
public interface CreateExampleCommand {

    Example execute(Example example);
}
