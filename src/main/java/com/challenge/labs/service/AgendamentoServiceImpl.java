package com.challenge.labs.service;

import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.model.Agendamento;
import com.challenge.labs.model.Destinatario;
import com.challenge.labs.model.Notificacao;
import com.challenge.labs.model.enums.StatusEnvio;
import com.challenge.labs.repository.AgendamentoRepository;
import com.challenge.labs.repository.DestinatarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ModelMapper modelMapper;
    private final NotificadorService notificadorService;
    private final DestinatarioRepository destinatarioRepository;

    @Override
    public AgendamentoDTO agendar(AgendamentoDTO dtoAgendamento) {
        Agendamento agendamento = modelMapper.map(dtoAgendamento, Agendamento.class);
        agendamento.setUuid(UUID.randomUUID());
        Destinatario destinatario = destinatarioRepository.findByCpf(dtoAgendamento.getDestinatario());
        agendamento.setDestinatario(destinatario);
        dtoAgendamento = modelMapper.map(agendamentoRepository.save(agendamento), AgendamentoDTO.class);
        this.criarNotificacao(agendamento);
        return dtoAgendamento;
    }

    @Override
    public AgendamentoDTO consultarAgendamento(String uuid) {
        return modelMapper.map(agendamentoRepository.findAllByUuid(uuid), AgendamentoDTO.class);
    }


    @Override
    public void cancelarAgendamento(String uuid) {
        Notificacao notificacao = notificadorService.recuperarNotificacaoPorAgendamento(uuid);
        notificadorService.cancelarNotificacaoDeAgendamento(notificacao);
    }

    private void criarNotificacao(Agendamento agendamento) {
        Notificacao notificacao = Notificacao.builder()
                .agendamento(agendamento)
                .status(StatusEnvio.AGUARDANDO)
                .dataAgendamento(agendamento.getDataAgendamento())
                .uuid(agendamento.getUuid().toString())
                .build();
        notificadorService.salvarNotificacao(notificacao);
    }
}
