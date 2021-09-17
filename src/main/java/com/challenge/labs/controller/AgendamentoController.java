package com.challenge.labs.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    @PostMapping("/v1/cancelar/{uuid}")
    public ResponseEntity<Void> cancelamentoComunicacao(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

}
