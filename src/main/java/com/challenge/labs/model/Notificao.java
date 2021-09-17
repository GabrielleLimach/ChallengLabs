package com.challenge.labs.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notificao {

    private Long id;
    private Agendamento agendamento;
    private LocalDateTime dataAgendamento;
    private String uuid;
}
