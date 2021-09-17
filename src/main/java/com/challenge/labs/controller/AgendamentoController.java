package com.challenge.labs.controller;


import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping("/v1/agendar")
    public ResponseEntity<AgendamentoDTO> agendamentoComunicao(@RequestBody AgendamentoDTO dtoAgendamento) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        dtoAgendamento = agendamentoService.agendar(dtoAgendamento);
        return ResponseEntity.created(location).body(dtoAgendamento);
    }


    @GetMapping("/v1/consultar/{uuid}")
    public ResponseEntity<AgendamentoDTO> consultaComunicao(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(agendamentoService.consultarAgendamento(uuid));
    }


    @PostMapping("/v1/cancelar/{uuid}")
    public ResponseEntity<Void> cancelamentoComunicacao(@PathVariable("uuid") String uuid) {
        agendamentoService.cancelarAgendamento(uuid);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

}
