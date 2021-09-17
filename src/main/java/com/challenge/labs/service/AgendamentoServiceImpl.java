package com.challenge.labs.service;

import com.challenge.labs.dtos.AgendamentoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {
    @Override
    public AgendamentoDTO agendar(AgendamentoDTO agendamento) {
        return null;
    }

    @Override
    public AgendamentoDTO consultarAgendamento(String uuid) {
        return null;
    }

    @Override
    public void cancelarAgendamento(String uuid) {

    }
}
