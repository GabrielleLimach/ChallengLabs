DO
$$
    DECLARE

        ids  int[] := NULL;
        nome varchar;
        id   BIGINT;
    BEGIN
        ids := Array(select array_agg(ag02_id) as id
                     from agendamento.ag02_destinatario);
        FOREACH id in array ids
            LOOP
                nome := (select ag02_nome || '@gmail.com' as nome
                         from agendamento.ag02_destinatario
                         where ag02_id = id);
                INSERT INTO agendamento.ag04_contato (ag04_id, ag04_endereco, ag04_tipo, ag04_status, ag04_id_destinatario)
                VALUES (DEFAULT, nome, 1, 0, id);
            END LOOP;
    END
$$;

DO
$$
    DECLARE

        ids      int[] := NULL;
        telefone varchar;
        id       BIGINT;
    BEGIN
        ids := Array(select array_agg(ag02_id) as id
                     from agendamento.ag02_destinatario);
        FOREACH id in array ids
            LOOP
                telefone := (SELECT '(' || array_to_string(
                        ARRAY(SELECT chr((48 + round(random() * 9)) :: integer)
                              FROM generate_series(1, 2)),
                        '') || ')' || array_to_string(
                                            ARRAY(SELECT chr((48 + round(random() * 9)) :: integer)
                                                  FROM generate_series(1, 9)), ''));
                INSERT INTO agendamento.ag04_contato (ag04_id, ag04_endereco, ag04_tipo, ag04_status, ag04_id_destinatario)
                VALUES (DEFAULT, telefone, 2, 0, id);
            END LOOP;
    END
$$;