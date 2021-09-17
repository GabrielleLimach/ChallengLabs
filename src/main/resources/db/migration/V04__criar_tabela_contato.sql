create table if not exists agendamento.ag04_contato
(
    ag04_id              bigserial    not null
        constraint ag04_contato_pk
            primary key,
    ag04_endereco        varchar(100) not null,
    ag04_tipo            integer,
    ag04_status          integer      not null,
    ag04_id_destinatario bigint       not null
        constraint ag04_fk_destinatario
            references agendamento.ag02_destinatario
);

alter table agendamento.ag04_contato
    owner to postgres;

create unique index if not exists ag04_contato_ag04_id_uindex
    on agendamento.ag04_contato (ag04_id);

