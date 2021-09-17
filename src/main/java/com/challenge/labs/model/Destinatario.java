package com.challenge.labs.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "agendamento", name = "ag02_destinatario")
public class Destinatario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ag02_id")
    private Long id;

    @Column(name ="ag02_nome")
    private String nome;

    @Column(name ="ag02_cpf")
    private String cpf;
}
