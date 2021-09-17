package com.challenge.labs.service.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailObserver implements NotificacaoObserver {

    @Override
    public void enviarNotificacao() {
        log.info("Enviando mensagem por email");
    }
}
