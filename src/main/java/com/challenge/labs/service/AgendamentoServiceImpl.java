package com.challenge.labs.service;

import com.challenge.labs.controller.AgendamentoController;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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
        agendamento.setUuid(UUID.randomUUID().toString());
        Destinatario destinatario = destinatarioRepository.findByCpf(dtoAgendamento.getDestinatario());
        agendamento.setDestinatario(destinatario);
        dtoAgendamento = modelMapper.map(agendamentoRepository.save(agendamento), AgendamentoDTO.class);
        this.criarNotificacao(agendamento);
        return this.criarHiperLink(dtoAgendamento);
    }

    @Override
    public AgendamentoDTO consultarAgendamento(String uuid) {
        Agendamento agendamento = agendamentoRepository.findAllByUuid(uuid);
        AgendamentoDTO dto =  modelMapper.map(agendamento, AgendamentoDTO.class);
        return  dto;
    }


    @Override
    public void cancelarAgendamento(String uuid) {
        Notificacao notificacao = notificadorService.recuperarNotificacaoPorAgendamento(uuid);
        notificadorService.cancelarNotificacaoDeAgendamento(notificacao);
    }


    private AgendamentoDTO criarHiperLink(AgendamentoDTO dtoAgendamento) {
        dtoAgendamento.add(linkTo(methodOn(AgendamentoController.class).consultaComunicao(dtoAgendamento.getUuid())).withRel("consultar"));
        dtoAgendamento.add(linkTo(methodOn(AgendamentoController.class).cancelamentoComunicacao(dtoAgendamento.getUuid())).withRel("cancelar"));
        return dtoAgendamento;
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
