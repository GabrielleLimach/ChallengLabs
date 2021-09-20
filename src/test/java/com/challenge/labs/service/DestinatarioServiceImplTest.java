package com.challenge.labs.service;

import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.model.Agendamento;
import com.challenge.labs.model.Contato;
import com.challenge.labs.model.Destinatario;
import com.challenge.labs.model.Notificacao;
import com.challenge.labs.model.enums.StatusEnvio;
import com.challenge.labs.model.enums.TipoContato;
import com.challenge.labs.model.exception.DestinatarioInvalidoException;
import com.challenge.labs.model.exception.ObjetoNaoEncontradoException;
import com.challenge.labs.repository.DestinatarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class DestinatarioServiceImplTest {
    @Mock
    DestinatarioRepository destinatarioRepository;
    @Mock
    Logger log;
    @InjectMocks
    DestinatarioServiceImpl destinatarioServiceImpl;
    Agendamento agendamento;
    Destinatario destinatario;
    AgendamentoDTO agendamentoDto;
    Notificacao notificacao;
    List<Notificacao> notificacaoList;
    Contato contato;
    List<Contato> contatoList;

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
        contato = new Contato();
        contato.setDestinatario(destinatario);
        contato.setTipoContato(TipoContato.EMAIL);
        contato.setStatus(0);
        contato.setEndereco("gabrielle@gmail.com");
        contatoList = new ArrayList<>();
        contatoList.add(contato);
    }

    @Test
    public void testRecuperarDestinatario() {
        when(destinatarioRepository.findByCpf(anyString())).thenReturn(destinatario);

        Destinatario result = destinatarioServiceImpl.recuperarDestinatario(agendamentoDto);
        Assert.assertEquals(destinatario, result);
    }

    @Test
    public void deveLancarUmaExcessaoCasoNaoLocalizeOdestinatario() {
        when(destinatarioRepository.findByCpf(anyString())).thenReturn(null);
        Assertions.assertThatCode(() -> destinatarioServiceImpl.recuperarDestinatario(agendamentoDto))
                .isInstanceOf(DestinatarioInvalidoException.class)
                .hasMessage("Não foi possivel localizar o destinatário verifique se o cpf esta correto: " + agendamento.getDestinatario().getNome());

    }
}
