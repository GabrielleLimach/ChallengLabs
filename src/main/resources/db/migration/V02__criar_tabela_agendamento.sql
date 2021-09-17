create table if not exists agendamento.ag01_agendamento
(
    ag01_id               bigserial   not null
        constraint ag01_agendamento_pk
            primary key,
    ag01_id_destinatario  bigint      not null
        constraint ag01_fk_agendamento
            references agendamento.ag02_destinatario,
    ag01_mensagem         varchar(50) not null,
    ag01_tipo             integer,
    ag01_data_agendamento timestamp   not null,
    ag01_uuid             varchar(36) not null
);

alter table agendamento.ag01_agendamento
    owner to postgres;

create unique index if not exists ag01_agendamento_ag01_uuid_uindex
    on agendamento.ag01_agendamento (ag01_uuid);

