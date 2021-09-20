package com.challenge.labs.service;

import com.challenge.labs.controller.AgendamentoController;
import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.model.Agendamento;
import com.challenge.labs.model.Contato;
import com.challenge.labs.model.Notificacao;
import com.challenge.labs.model.enums.StatusEnvio;
import com.challenge.labs.model.exception.AgendamentoInvalidoException;
import com.challenge.labs.repository.AgendamentoRepository;
import com.challenge.labs.repository.ContatosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    private final DestinatarioService destinatarioService;
    private final ContatosRepository contatosRepository;


    @Override
    public AgendamentoDTO agendar(AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = montarAgendamento(agendamentoDTO);
        agendamentoDTO = modelMapper.map(agendamento, AgendamentoDTO.class);
        return this.criarHiperLink(agendamentoDTO);
    }

    @Override
    public AgendamentoDTO consultarAgendamento(String uuid) {
        Agendamento agendamento = Optional.ofNullable(agendamentoRepository.findByUuid(uuid))
                .orElseThrow(() ->
                        new AgendamentoInvalidoException("Não foi possivel localizar um agendamento para esse uuid " + uuid));

        AgendamentoDTO dto = modelMapper.map(agendamento, AgendamentoDTO.class);
        return this.criarHiperLink(dto);
    }


    @Override
    public void cancelarAgendamento(String uuid) {
        List<Notificacao> notificacaoList = notificadorService.recuperarNotificacaoPorAgendamento(uuid);
        notificacaoList.forEach(notificadorService::cancelarNotificacaoDeAgendamento);
    }

    @Override
    public Agendamento montarAgendamento(AgendamentoDTO dtoAgendamento) {
        Agendamento agendamento = modelMapper.map(dtoAgendamento, Agendamento.class);
        agendamento.setUuid(UUID.randomUUID().toString());
        agendamento.setDestinatario(destinatarioService.recuperarDestinatario(dtoAgendamento));
        agendamento.setMensagem(dtoAgendamento.getMensagem());
        agendamento = agendamentoRepository.save(agendamento);
        this.criarNotificacao(agendamento);
        return agendamento;
    }

    private AgendamentoDTO criarHiperLink(AgendamentoDTO dtoAgendamento) {
        dtoAgendamento.add(linkTo(methodOn(AgendamentoController.class).consultaComunicao(dtoAgendamento.getUuid())).withRel("consultar"));
        dtoAgendamento.add(linkTo(methodOn(AgendamentoController.class).cancelamentoComunicacao(dtoAgendamento.getUuid())).withRel("cancelar"));
        return dtoAgendamento;
    }

    private void criarNotificacao(Agendamento agendamento) {
        List<Contato> contatoList = contatosRepository.findAllByDestinatario(agendamento.getDestinatario());
        contatoList.forEach(contato -> {
                    Notificacao notificacao = Notificacao.builder()
                            .agendamento(agendamento)
                            .status(StatusEnvio.AGUARDANDO)
                            .dataAgendamento(agendamento.getDataAgendamento())
                            .tipo(contato.getTipoContato())
                            .uuid(agendamento.getUuid().toString())
                            .build();
                    notificadorService.salvarNotificacao(notificacao);
                }
        );
    }
}
