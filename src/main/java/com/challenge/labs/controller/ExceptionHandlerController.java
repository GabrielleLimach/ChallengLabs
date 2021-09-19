package com.challenge.labs.controller;

import com.challenge.labs.model.exception.AgendamentoInvalidoException;
import com.challenge.labs.model.exception.DestinatarioInvalidoException;
import com.challenge.labs.model.exception.ObjetoNaoEncontradoException;
import com.challenge.labs.model.handle.StandardErrorHandler;
import com.challenge.labs.model.handle.ValidationErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorHandler> handlerValidacaoArgumentoInvalido(MethodArgumentNotValidException e, HttpServletRequest request) {

        ValidationErrorHandler err = this.geraValidationErrorHandler(HttpStatus.BAD_REQUEST, e, request);
        err.setMessage("Falha ao validar os campos do json");

        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        fieldErrorList.forEach(f -> err.addFieldMessageErrorHandler(f.getField(), f.getDefaultMessage()));

        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardErrorHandler> handlerValidacaoConstraintViolada(ConstraintViolationException e, HttpServletRequest request) {

        ValidationErrorHandler err = this.geraValidationErrorHandler(HttpStatus.BAD_REQUEST, e, request);
        err.setMessage("Falha ao validar os campos do json");

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        violations.forEach(f -> err.addFieldMessageErrorHandler(f.getPropertyPath().toString(), f.getMessageTemplate()));

        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<StandardErrorHandler> hanlderValidacaoTipoInvalido(UnexpectedTypeException e, HttpServletRequest request) {
        StandardErrorHandler err = this.geraStandarErrorHandler(HttpStatus.BAD_REQUEST, e, request);
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<StandardErrorHandler> handlerBD(SQLException e, HttpServletRequest request) {
        StandardErrorHandler err = this.geraStandarErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorHandler> handlerAll(Exception e, HttpServletRequest request) {
        StandardErrorHandler err = this.geraStandarErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardErrorHandler> handlerValidacaoJackson(HttpMessageNotReadableException e, HttpServletRequest request) {
        StandardErrorHandler err = this.geraStandarErrorHandler(HttpStatus.BAD_REQUEST, e, request);
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<StandardErrorHandler> handlerObjetoNaoEncontrado(ObjetoNaoEncontradoException e, HttpServletRequest request) {
        StandardErrorHandler err = this.geraStandarErrorHandler(HttpStatus.BAD_REQUEST, e, request);
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(DestinatarioInvalidoException.class)
    public ResponseEntity<StandardErrorHandler> handlerTituloPagarInvalido(DestinatarioInvalidoException e, HttpServletRequest request) {
        StandardErrorHandler err = this.geraStandarErrorHandler(HttpStatus.BAD_REQUEST, e, request);
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AgendamentoInvalidoException.class)
    public ResponseEntity<StandardErrorHandler> handlerCartaCreditoInvalida(AgendamentoInvalidoException e, HttpServletRequest request) {
        StandardErrorHandler err = this.geraStandarErrorHandler(HttpStatus.BAD_REQUEST, e, request);
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    private ValidationErrorHandler geraValidationErrorHandler(HttpStatus httpStatus, Exception e, HttpServletRequest request) {
        return new ValidationErrorHandler(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.name(),
                e.getMessage(),
                request.getServletPath());
    }

    private StandardErrorHandler geraStandarErrorHandler(HttpStatus httpStatus, Exception e, HttpServletRequest request) {
        return new StandardErrorHandler(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.name(),
                e.getMessage(),
                request.getServletPath());
    }

}