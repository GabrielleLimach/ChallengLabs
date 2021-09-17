package com.challenge.labs.service;

import com.challenge.labs.dtos.AgendamentoDTO;

public interface AgendamentoService {

    AgendamentoDTO agendar(AgendamentoDTO agendamento);

    AgendamentoDTO consultarAgendamento(String uuid);

    void cancelarAgendamento(String uuid);
}
