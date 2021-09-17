create table if not exists agendamento.ag03_notificacao
(
    ag03_id               bigserial   not null
        constraint ag03_notificacao_pk
            primary key,
    ag03_id_agendamento   bigint      not null
        constraint ag03_fk_agendamento
            references agendamento.ag01_agendamento,
    ag03_status           integer     not null,
    ag03_data_agendamento timestamp   not null,
    ag03_uuid             varchar(36) not null
        constraint ag03_fk_uuid
            references agendamento.ag01_agendamento (ag01_uuid)
);

alter table agendamento.ag03_notificacao
    owner to postgres;

create unique index if not exists ag03_notificacao_ag03_id_uindex
    on agendamento.ag03_notificacao (ag03_id);

