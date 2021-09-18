package com.challenge.labs.service;

import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.model.Agendamento;
import com.challenge.labs.model.Destinatario;
import com.challenge.labs.model.Notificacao;
import com.challenge.labs.model.enums.StatusEnvio;
import com.challenge.labs.repository.AgendamentoRepository;
import com.challenge.labs.repository.DestinatarioRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class AgendamentoServiceImplTest {
    @Mock
    AgendamentoRepository agendamentoRepository;
    @Mock
    ModelMapper modelMapper;
    @Mock
    NotificadorService notificadorService;
    @Mock
    DestinatarioRepository destinatarioRepository;
    @Mock
    Logger log;
    @InjectMocks
    AgendamentoServiceImpl agendamentoServiceImpl;
    Agendamento agendamento;
    Destinatario destinatario;
    AgendamentoDTO agendamentoDto;
    Notificacao notificacao;
    List<Notificacao> notificacaoList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        destinatario = new Destinatario();
        modelMapper = mock(ModelMapper.class);

        destinatario.setCpf("21345678954");
        destinatario.setNome("Usuario");
        agendamento = new Agendamento();
        agendamento.setDestinatario(destinatario);
        agendamento.setDataAgendamento(LocalDateTime.of(2022, 02, 01, 01, 0));
        agendamento.setMensagem("Lembrete Labs");
        agendamento.setUuid(UUID.randomUUID().toString());
        agendamentoDto = new AgendamentoDTO();
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
    public void testAgendar(){
        when(destinatarioRepository.findByCpf(anyString())).thenReturn(new Destinatario());

        AgendamentoDTO result = agendamentoServiceImpl.agendar(new AgendamentoDTO());
        Assert.assertEquals(new AgendamentoDTO(), result);
    }

    @Test
    public void testConsultarAgendamento(){
        when(agendamentoRepository.findAllByUuid(agendamento.getUuid())).thenReturn(agendamento);
        when(modelMapper.map(agendamento, AgendamentoDTO.class)).thenReturn(agendamentoDto);

        AgendamentoDTO result = agendamentoServiceImpl.consultarAgendamento(agendamento.getUuid());
        Assert.assertEquals(agendamentoDto, result);
    }

    @Test
    public void testCancelarAgendamento(){
        when(notificadorService.recuperarNotificacaoPorAgendamento(agendamento.getUuid())).thenReturn(notificacao);

        agendamentoServiceImpl.cancelarAgendamento(agendamento.getUuid());
    }
}