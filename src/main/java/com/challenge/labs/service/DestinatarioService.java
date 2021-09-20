package com.challenge.labs.service;

import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.model.Destinatario;

public interface DestinatarioService {

    Destinatario recuperarDestinatario(AgendamentoDTO dtoAgendamento);

}
