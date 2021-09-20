package com.challenge.labs.repository;

import com.challenge.labs.model.Notificacao;
import com.challenge.labs.model.enums.StatusEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findTop100ByStatus(StatusEnvio statusEnvio);

    Notificacao findNotificacaoByUuid(String uuid);

    List<Notificacao> findAllByUuid(String uuid);

}
