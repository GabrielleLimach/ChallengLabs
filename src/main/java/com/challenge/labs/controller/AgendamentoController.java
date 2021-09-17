package com.challenge.labs.controller;


import com.challenge.labs.dtos.AgendamentoDTO;
import com.challenge.labs.service.AgendamentoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @ApiOperation(value = "Rota para Agendamento de envio de comunicação",
            notes = "Essa rota retornar um Agendamento criado")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna um agendamento criado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/v1/agendar")
    public ResponseEntity<AgendamentoDTO> agendamentoComunicao(@RequestBody @Valid AgendamentoDTO dtoAgendamento) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        dtoAgendamento = agendamentoService.agendar(dtoAgendamento);
        return ResponseEntity.created(location).body(dtoAgendamento);
    }

    @ApiOperation(value = "Rota Consulta do envio da comunicação",
            notes = "Essa rota retorna um Agendamento")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um agendamento criado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/v1/consultar/{uuid}")
    public ResponseEntity<AgendamentoDTO> consultaComunicao(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(agendamentoService.consultarAgendamento(uuid));
    }

    @ApiOperation(value = "Rota Cancelamento do envio da comunicação")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um agendamento criado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/v1/cancelar/{uuid}")
    public ResponseEntity<Void> cancelamentoComunicacao(@PathVariable("uuid") String uuid) {
        agendamentoService.cancelarAgendamento(uuid);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
