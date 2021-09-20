create table if not exists agendamento.ag02_destinatario
(
    ag02_id   bigserial   not null
        constraint ag01_cliente_pk
            primary key,
    ag02_nome varchar(50) not null,
    ag02_cpf  varchar(14) not null
);

alter table agendamento.ag02_destinatario
    owner to postgres;


INSERT INTO agendamento.ag02_destinatario (ag02_id, ag02_nome, ag02_cpf)
VALUES (DEFAULT, 'Gabrielle', '12345678945');