DO
$$
    DECLARE

        ids             int[] := NULL;
        id              BIGINT;
        uuid            varchar;
        dataAgendamento timestamp;
        nome            varchar;
    BEGIN
        ids := Array(select array_agg(ag02_id) as id
                     from agendamento.ag02_destinatario);
        FOREACH id in array ids
            LOOP
                uuid := (SELECT gen_random_uuid());
                dataAgendamento := (select NOW() + (random() * (interval '90 days')) + '30 days');
                nome := (select ag02_nome as nome
                         from agendamento.ag02_destinatario
                         where ag02_id = id);
                INSERT INTO agendamento.ag01_agendamento (ag01_id, ag01_id_destinatario, ag01_mensagem,
                                                          ag01_data_agendamento, ag01_uuid)
                VALUES (DEFAULT, id, 'Olá ' || nome || ' tem uma nova mensagem', dataAgendamento,
                        uuid);
            END LOOP;
    END
$$;