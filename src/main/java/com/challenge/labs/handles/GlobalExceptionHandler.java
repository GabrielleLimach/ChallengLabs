package com.challenge.labs.handles;


import com.challenge.labs.model.handles.StandardError;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.LocalDateTime;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    protected static final String TIME_MEASURE = "CHALLENGE-LABS-API-EXECUTE-TIME-TRACKER";
    protected static final String REQ_UUID = "req.uuid";
    protected static final String REQ_URL = "req.url";
    protected static final String REQ_QUERY = "req.query";
    protected static final String REQ_HOST = "req.host";
    protected static final String REQ_METHOD = "req.method";
    protected static final String REQ_CPF = "req.cpf";
    protected static final String REQ_PARAMS = "req.params";
    protected static final String RSP_ELAPSED_TIME = "rsp.elapsed.time";
    protected static final String RSP_STATUS = "rsp.status";
    private static final String TIME_ID = "CHALLENGE-LABS-API-REQUEST-ID-TRACKER";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<StandardError> handleAllExceptions(Exception ex, HttpServletRequest request) {
        StandardError erro = setarStandartErro(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
        registerLog(erro, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder erroMessage = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            erroMessage.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage()).append(". ");
        }
        NativeWebRequest webRequest = (NativeWebRequest) request;
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        StandardError erro = setarStandartErro(HttpStatus.BAD_REQUEST, erroMessage.toString(), servletRequest);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<StandardError> constraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        String message = "";
        for (ConstraintViolation violations : ex.getConstraintViolations()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(violations.getPropertyPath().toString());
            message = stringBuilder.substring(stringBuilder.indexOf(".") + 1) + " " + violations.getMessage();
        }
        StandardError erro = setarStandartErro(HttpStatus.BAD_REQUEST, message, request);
        registerLog(erro, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<StandardError> numberFormat(NumberFormatException e, HttpServletRequest request) {
        StandardError erro = setarStandartErro(
                HttpStatus.BAD_REQUEST,
                "Tipo do parâmetro inválido: " + e.getMessage(),
                request
        );
        registerLog(erro, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> methodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        StandardError erro = setarStandartErro(
                HttpStatus.BAD_REQUEST,
                "Tipo do parâmetro inválido: " + e.getMessage(),
                request
        );
        registerLog(erro, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<StandardError> invalidFormat(InvalidFormatException e, HttpServletRequest request) {
        StandardError erro = setarStandartErro(
                HttpStatus.BAD_REQUEST,
                "Formato inválido" + e.getMessage(),
                request
        );
        registerLog(erro, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<StandardError> errorInDataBase(SQLException e, HttpServletRequest request) {
        StandardError erro = setarStandartErro(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Erro ao executar a query: " + e.getMessage(),
                request
        );
        registerLog(erro, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<StandardError> objectNull(NullPointerException e, HttpServletRequest request) {
        StandardError erro = setarStandartErro(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Objeto nulo ou vazio: " + e.getMessage(),
                request
        );
        registerLog(erro, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<StandardError> errorInIbatis(PersistenceException e, HttpServletRequest request) {
        StandardError erro = setarStandartErro(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro no Banco de dados " + e.getMessage(),
                request
        );
        this.registerLog(erro, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    private void registerLog(StandardError erro, HttpServletRequest request) {
        long startTime = (long) request.getAttribute(TIME_MEASURE);

        MDC.put(RSP_STATUS, String.valueOf(erro.getValue()));
        MDC.put(RSP_ELAPSED_TIME, String.valueOf(System.currentTimeMillis() - startTime));

        if (log.isErrorEnabled()) log.error("Request finished - " + erro.toString());

        MDC.remove(REQ_UUID);
        MDC.remove(REQ_URL);
        MDC.remove(REQ_QUERY);
        MDC.remove(REQ_HOST);
        MDC.remove(REQ_METHOD);
        MDC.remove(REQ_PARAMS);
        MDC.remove(RSP_ELAPSED_TIME);
        MDC.remove(RSP_STATUS);
        MDC.remove(REQ_CPF);
    }

    private StandardError setarStandartErro(HttpStatus status, String mensagem, HttpServletRequest request) {
        return StandardError.builder()
                .value(status.value())
                .message(mensagem)
                .details(request.getRequestURI())
                .currentTimeMillis(LocalDateTime.now()).build();
    }
}
