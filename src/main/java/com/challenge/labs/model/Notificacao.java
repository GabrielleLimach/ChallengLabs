package com.challenge.labs.model;

import com.challenge.labs.model.enums.StatusEnvio;
import com.challenge.labs.model.enums.TipoContato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "ag03_tipo_contato")
    @Enumerated(EnumType.ORDINAL)
    private TipoContato tipo;

    @Column(name = "ag03_data_agendamento")
    private LocalDateTime dataAgendamento;

    @Column(name = "ag03_uuid")
    private String uuid;
}
