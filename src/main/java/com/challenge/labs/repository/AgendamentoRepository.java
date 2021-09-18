package com.challenge.labs.repository;

import com.challenge.labs.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    Agendamento findByUuid(String uuid);

}
