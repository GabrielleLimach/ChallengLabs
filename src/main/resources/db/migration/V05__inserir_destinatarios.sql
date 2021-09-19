DO
$$
    DECLARE
nomes text[] := array ['Miguel','Arthur','Heitor','Helena','Alice','Theo','Davi','Laura','Gabriel','Gael','Bernardo','Samuel','Valentina','João Miguel','Enzo Gabriel','Heloísa','Pedro','Lorenzo','Sophia','Maria Clara','Maria Júlia','Maria Eduarda','Lorena','Lucas','Manuela','Cecília','Maria Cecília','Benício','Júlia','Isabella','Lívia','Pedro Henrique','Maria Luiza','Guilherme','Maria Alice','Joaquim','Manuella','Eloa','Rafael','João Pedro','Antonella','Matheus','Isadora','Nicolas','Isaac','Henrique','Gustavo','Benjamin','Maite','Anthony'];
        cpf   varchar(14);
        nome  text;
BEGIN
        FOREACH nome in array nomes
            LOOP
                raise notice '%' ,nome;
                cpf := (SELECT array_to_string(
                                       ARRAY(SELECT chr((48 + round(random() * 9)) :: integer)
                                             FROM generate_series(1, 11)),
                                       ''));
INSERT INTO agendamento.ag02_destinatario (ag02_id, ag02_nome, ag02_cpf) VALUES (DEFAULT, nome, cpf);
END LOOP;
END
$$;