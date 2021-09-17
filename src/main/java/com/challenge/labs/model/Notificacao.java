package com.challenge.labs.model;

import com.challenge.labs.model.enums.StatusEnvio;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "agendamento", name = "ag03_notificacao")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ag03_id")
    private Long id;

    @Column(name = "ag03_status")
    @Enumerated(EnumType.ORDINAL)
    private StatusEnvio status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ag03_id_agendamento")
    private Agendamento agendamento;

    @Column(name = "ag03_data_agendamento")
    private LocalDateTime dataAgendamento;

    @Column(name = "ag03_uuid")
    private String uuid;
}
