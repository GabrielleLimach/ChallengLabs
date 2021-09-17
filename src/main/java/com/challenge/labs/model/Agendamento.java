package com.challenge.labs.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Agendamento {

    private Long id;
    private Destinatario destinatario;
    private LocalDateTime dataAgendamento = LocalDateTime.now();
    private String mensagem;
    private Integer tipo = 1;
    private UUID uuid = getUuid();

}
