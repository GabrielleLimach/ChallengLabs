package com.challenge.labs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "agendamento", name = "ag01_agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ag01_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ag01_id_destinatario", referencedColumnName = "ag02_id")
    private Destinatario destinatario;

    @Column(name = "ag01_data_agendamento")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAgendamento = LocalDateTime.now();

    @Column(name = "ag01_mensagem")
    private String mensagem;

    @Column(name = "ag01_uuid")
    private String uuid;

}
