package com.challenge.labs.repository;

import com.challenge.labs.model.Contato;
import com.challenge.labs.model.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContatosRepository extends JpaRepository<Contato, Long> {

    List<Contato> findAllByDestinatario(Destinatario destinatario);
}
