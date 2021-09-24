package com.challenge.labs.model;

import com.challenge.labs.model.enums.StatusContato;
import com.challenge.labs.model.enums.TipoContato;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "agendamento", name = "ag04_contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ag04_id")
    private Long id;

    @Column(name = "ag04_endereco")
    private String endereco;

    @Column(name = "ag04_tipo")
    @Enumerated(EnumType.ORDINAL)
    private TipoContato tipoContato;

    @Column(name = "ag04_status")
    @Enumerated(EnumType.ORDINAL)
    private StatusContato status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ag04_id_destinatario", referencedColumnName = "ag02_id")
    private Destinatario destinatario;
}
