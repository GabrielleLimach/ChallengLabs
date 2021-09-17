package com.challenge.labs.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoDTO {

    private String destinatario;
    private String uuid;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAgendamento;
    private String mensagem;
}
