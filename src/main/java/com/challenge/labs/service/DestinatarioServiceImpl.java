package com.challenge.labs.service;

import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.model.Destinatario;
import com.challenge.labs.model.exception.DestinatarioInvalidoException;
import com.challenge.labs.repository.DestinatarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DestinatarioServiceImpl implements DestinatarioService {

    private final DestinatarioRepository destinatarioRepository;

    @Override
    public Destinatario recuperarDestinatario(AgendamentoDTO dto) {
        Destinatario destinatario = Optional.ofNullable(destinatarioRepository.findByCpf(dto.getDestinatario()))
                .orElseThrow(() ->
                        new DestinatarioInvalidoException("Não foi possivel localizar o destinatário verifique se o cpf esta correto: " + dto.getDestinatario()));

        return destinatario;
    }
}
