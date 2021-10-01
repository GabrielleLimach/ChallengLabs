package com.challenge.labs.service;

import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.model.Agendamento;
import com.challenge.labs.model.Contato;
import com.challenge.labs.model.Destinatario;
import com.challenge.labs.model.Notificacao;
import com.challenge.labs.model.enums.StatusContato;
import com.challenge.labs.model.enums.StatusEnvio;
import com.challenge.labs.model.enums.TipoContato;
import com.challenge.labs.repository.AgendamentoRepository;
import com.challenge.labs.repository.ContatosRepository;
import com.challenge.labs.repository.DestinatarioRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
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
    @Spy
    ModelMapper modelMapper;
    @Mock
    NotificadorService notificadorService;
    @Mock
    DestinatarioRepository destinatarioRepository;
    @Mock
    DestinatarioService destinatarioService;
    @Mock
    ContatosRepository contatosRepository;
    @Mock
    Logger log;
    @InjectMocks
    AgendamentoServiceImpl agendamentoServiceImpl;
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
        contato.setStatus(StatusContato.ATIVO);
        contato.setEndereco("gabrielle@gmail.com");
        contatoList = new ArrayList<>();
        contatoList.add(contato);
    }

    @Test
    public void testAgendar(){
        when(destinatarioRepository.findByCpf(any())).thenReturn(destinatario);
        when(agendamentoRepository.save(any())).thenReturn(agendamento);
        when(destinatarioService.recuperarDestinatario(any())).thenReturn(destinatario);
        when(contatosRepository.findAllByDestinatario(any())).thenReturn(contatoList);
        when(modelMapper.map(agendamento, AgendamentoDTO.class)).thenReturn(agendamentoDto);
        AgendamentoDTO result = agendamentoServiceImpl.agendar(agendamentoDto);
        Assert.assertEquals(agendamentoDto, result);
    }

    @Test
    public void testConsultarAgendamento(){
        when(agendamentoRepository.findByUuid(any())).thenReturn(agendamento);
        spy(modelMapper.map(agendamento, AgendamentoDTO.class));
        Assert.assertNotNull(agendamentoServiceImpl.consultarAgendamento(agendamento.getUuid()));
    }

    @Test
    public void testCancelarAgendamento(){
        when(notificadorService.recuperarNotificacaoPorAgendamento(agendamento.getUuid())).thenReturn(notificacaoList);

        agendamentoServiceImpl.cancelarAgendamento(agendamento.getUuid());
        verify(notificadorService, Mockito.times(1)).cancelarNotificacaoDeAgendamento(notificacao);
    }
}