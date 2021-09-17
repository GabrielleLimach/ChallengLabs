package com.challenge.labs.model;

import lombok.Data;

@Data
public class Mensagem {

    private Destinatario cliente;
    private String texto;
}
