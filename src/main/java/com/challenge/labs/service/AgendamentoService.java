package com.challenge.labs.service;

import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.model.Agendamento;

public interface AgendamentoService {

    AgendamentoDTO agendar(AgendamentoDTO agendamentoDTO);

    AgendamentoDTO consultarAgendamento(String uuid);

    void cancelarAgendamento(String uuid);

    Agendamento montarAgendamento(AgendamentoDTO dtoAgendamento);
}
