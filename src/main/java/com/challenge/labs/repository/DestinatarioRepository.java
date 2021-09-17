package com.challenge.labs.repository;

import com.challenge.labs.model.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinatarioRepository extends JpaRepository<Destinatario, Long> {

    Destinatario findByCpf(String cpf);
}
