package com.challenge.labs.service;

import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.model.Agendamento;
import com.challenge.labs.model.Destinatario;
import com.challenge.labs.model.Notificacao;
import com.challenge.labs.model.enums.StatusEnvio;
import com.challenge.labs.model.exception.ObjetoNaoEncontradoException;
import com.challenge.labs.repository.NotificacaoRepository;
import com.challenge.labs.service.observer.NotificacaoObserver;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NotificadorServiceTest {
    @Mock
    List<NotificacaoObserver> observers;
    @Mock
    NotificacaoRepository notificacaoRepository;
    @Mock
    Logger log;
    @InjectMocks
    NotificadorService notificadorService;
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
    public void testEnviarNotificacoes(){
        when(notificacaoRepository.findTop100ByStatus(StatusEnvio.AGUARDANDO)).thenReturn(notificacaoList);
        notificadorService.enviarNotificacoes();
        verify(notificacaoRepository, Mockito.times(1)).saveAll(notificacaoList);
        assertEquals(StatusEnvio.CONCLUIDO, notificacao.getStatus());
    }

    @Test
    public void testNotificar(){
        notificadorService.notificar();
    }

    @Test
    public void testSalvarNotificacao(){
        when(notificacaoRepository.findNotificacaoByUuid(agendamentoDto.getUuid())).thenReturn(notificacao);
        notificadorService.salvarNotificacao(notificacao);
        verify(notificacaoRepository, Mockito.times(1)).save(notificacao);
    }

    @Test
    public void testRecuperarNotificacaoPorAgendamento(){
        when(notificacaoRepository.findAllByUuid(agendamentoDto.getUuid())).thenReturn(notificacaoList);

        List<Notificacao> result = notificadorService.recuperarNotificacaoPorAgendamento(agendamentoDto.getUuid());
        assertEquals(notificacaoList, result);
    }

    @Test
    public void deveLancarUmaExcessaoCasoNaoLocalizeANotificacao() {
        when(notificacaoRepository.findNotificacaoByUuid(agendamentoDto.getUuid())).thenReturn(notificacao);
        Assertions.assertThatCode(() -> notificadorService.recuperarNotificacaoPorAgendamento(agendamentoDto.getUuid()))
                .isInstanceOf(ObjetoNaoEncontradoException.class)
                .hasMessage("Não foi possivel localizar um agendamento para esse uuid " + agendamentoDto.getUuid());

    }

    @Test
    public void testCancelarNotificacaoDeAgendamento(){
        notificadorService.cancelarNotificacaoDeAgendamento(notificacao);

        assertEquals(StatusEnvio.CANCELADA, notificacao.getStatus());
        verify(notificacaoRepository, Mockito.times(1)).save(notificacao);
    }

    @Test
    public void testRecuperarListaNotificacoesPorStatus(){
        when(notificacaoRepository.findTop100ByStatus(StatusEnvio.AGUARDANDO)).thenReturn(notificacaoList);

        List<Notificacao> result = notificadorService.recuperarListaNotificacoesPorStatus(StatusEnvio.AGUARDANDO);
        assertEquals(notificacaoList, result);
    }
}