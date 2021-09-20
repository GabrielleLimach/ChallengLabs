package com.challenge.labs.service;

import com.challenge.labs.model.Notificacao;
import com.challenge.labs.model.enums.StatusEnvio;
import com.challenge.labs.model.exception.ObjetoNaoEncontradoException;
import com.challenge.labs.repository.NotificacaoRepository;
import com.challenge.labs.service.observer.NotificacaoObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificadorService {

    private static final long SEGUNDO = 1000;
    private static final long MINUTO = SEGUNDO * 60;
    private static final long DOIS_MINUTOS = MINUTO * 2;

    private final List<NotificacaoObserver> observers;
    private final NotificacaoRepository notificacaoRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Scheduled(fixedDelay = DOIS_MINUTOS, initialDelay = DOIS_MINUTOS)
    public void enviarNotificacoes() {
        List<Notificacao> notificacaoList = this.recuperarListaNotificacoesPorStatus(StatusEnvio.AGUARDANDO);
        notificacaoList.stream()
                .filter(notificacao -> notificacao.getDataAgendamento().isAfter(LocalDateTime.now()))
                .forEach(notificacao -> {
                    notificacao.setStatus(StatusEnvio.CONCLUIDO);
                    notificar();
                });
        notificacaoRepository.saveAll(notificacaoList);
    }

    void notificar() {
        observers.forEach(NotificacaoObserver::enviarNotificacao);
    }

    void salvarNotificacao(Notificacao notificacao) {
        notificacaoRepository.save(notificacao);
    }

    List<Notificacao> recuperarNotificacaoPorAgendamento(String uuid) {
        List<Notificacao> notificacaoList = notificacaoRepository.findAllByUuid(uuid);
        if (notificacaoList.isEmpty())
            throw new ObjetoNaoEncontradoException("Não foi possivel localizar um agendamento para esse uuid " + uuid);

        return notificacaoList;
    }

    void cancelarNotificacaoDeAgendamento(Notificacao notificacao) {
        notificacao.setStatus(StatusEnvio.CANCELADA);
        notificacaoRepository.save(notificacao);
    }

    List<Notificacao> recuperarListaNotificacoesPorStatus(StatusEnvio statusEnvio) {
        return notificacaoRepository.findTop100ByStatus(statusEnvio);
    }

}
