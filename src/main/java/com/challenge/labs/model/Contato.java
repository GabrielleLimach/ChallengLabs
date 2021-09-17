package com.challenge.labs.model;

import lombok.Data;

@Data
public class Contato {

    private Long id;
    private String endereco;
    private Integer tipo;
    private Destinatario destinatario;
}
