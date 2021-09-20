package com.challenge.labs.controller;

import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.model.Agendamento;
import com.challenge.labs.model.Destinatario;
import com.challenge.labs.model.Notificacao;
import com.challenge.labs.model.enums.StatusEnvio;
import com.challenge.labs.service.AgendamentoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AgendamentoControllerTest {
    @Mock
    AgendamentoService agendamentoService;
    @InjectMocks
    AgendamentoController agendamentoController;
    Agendamento agendamento;
    Destinatario destinatario;
    AgendamentoDTO agendamentoDto;
    Notificacao notificacao;
    List<Notificacao> notificacaoList;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        destinatario = new Destinatario();
        destinatario.setCpf("21345678954");
        destinatario.setNome("Usuario");
        agendamento = new Agendamento();
        agendamento.setDestinatario(destinatario);
        agendamento.setDataAgendamento(LocalDateTime.of(2022, 02, 01, 01, 0));
        agendamento.setMensagem("Lembrete Labs");
        agendamento.setUuid(UUID.randomUUID().toString());
        agendamentoDto = AgendamentoDTO.builder()
                .destinatario(destinatario.getNome())
                .mensagem(agendamento.getMensagem())
                .dataAgendamento(agendamento.getDataAgendamento())
                .build();
        agendamentoDto.setDataAgendamento(agendamento.getDataAgendamento());
        agendamentoDto.setMensagem(agendamento.getMensagem());
        agendamentoDto.setDestinatario(destinatario.getNome());
        agendamentoDto.setUuid("8fcf3358-368a-4731-863a-d0f9ac4418d2");
        notificacao = Notificacao.builder()
                .agendamento(agendamento)
                .status(StatusEnvio.AGUARDANDO)
                .dataAgendamento(agendamento.getDataAgendamento())
                .uuid(agendamento.getUuid().toString())
                .build();
        notificacaoList = new ArrayList<>();
        notificacaoList.add(notificacao);
    }

    @Test
    public void testAgendamentoComunicao() {
        when(agendamentoService.agendar(agendamentoDto)).thenReturn(agendamentoDto);

        ResponseEntity<AgendamentoDTO> result = agendamentoController.agendamentoComunicao(agendamentoDto);
        Assert.assertEquals(result, ResponseEntity.created(null).body(agendamentoDto));
    }

    @Test
    public void testConsultaComunicao() {
        when(agendamentoService.consultarAgendamento(agendamento.getUuid())).thenReturn(agendamentoDto);
        ResponseEntity<AgendamentoDTO> result = agendamentoController.consultaComunicao(agendamento.getUuid());
        Assert.assertEquals(ResponseEntity.ok(agendamentoDto), result);
    }

    @Test
    public void testCancelamentoComunicacao() {
        ResponseEntity<Void> result = agendamentoController.cancelamentoComunicacao(agendamento.getUuid());
        verify(agendamentoService).cancelarAgendamento(agendamento.getUuid());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }
}