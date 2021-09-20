package com.challenge.labs.controller;

import com.challenge.labs.model.exception.AgendamentoInvalidoException;
import com.challenge.labs.model.exception.DestinatarioInvalidoException;
import com.challenge.labs.model.exception.ObjetoNaoEncontradoException;
import com.challenge.labs.model.handle.StandardErrorHandler;
import com.challenge.labs.model.handle.ValidationErrorHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ExceptionHandlerControllerTest {
    @Mock
    Logger log;
    @Mock
    private ObjetoNaoEncontradoException objetoNaoEncontradoException;
    @Mock
    private AgendamentoInvalidoException agendamentoInvalidoException;
    @Mock
    private DestinatarioInvalidoException destinatarioInvalidoException;
    @Mock
    private HttpMessageNotReadableException httpMessageNotReadableException;
    @Mock
    private Exception exception;
    @Mock
    private SQLException sqlException;
    @Mock
    private UnexpectedTypeException unexpectedTypeException;
    @Mock
    private ConstraintViolationException constraintViolationException;
    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    ExceptionHandlerController exceptionHandlerController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void testHandlerValidacaoArgumentoInvalido(){
        BindingResult bindingResult = mock(BindingResult.class);
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController
                .handlerValidacaoArgumentoInvalido(methodArgumentNotValidException, request);

        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ValidationErrorHandler());
        assertEquals(expected, result);
    }

    @Test
    public void testHandlerValidacaoConstraintViolada(){

        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController.handlerValidacaoConstraintViolada(constraintViolationException, request);
        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardErrorHandler());

        Assert.assertEquals(expected.getStatusCode(), result.getStatusCode());
        Assert.assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
    }

    @Test
    public void testHandlerDateTimeParseViolada(){
        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController.handlerDateTimeParseViolada(constraintViolationException, request);
        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardErrorHandler());

        Assert.assertEquals(expected.getStatusCode(), result.getStatusCode());
        Assert.assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
    }

    @Test
    public void testHandlerInvalidFormatException(){
        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController.handlerInvalidFormatException(constraintViolationException, request);
        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardErrorHandler());

        Assert.assertEquals(expected.getStatusCode(), result.getStatusCode());
        Assert.assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
    }

    @Test
    public void testHanlderValidacaoTipoInvalido(){
        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController.hanlderValidacaoTipoInvalido(unexpectedTypeException, request);
        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardErrorHandler());

        Assert.assertEquals(expected.getStatusCode(), result.getStatusCode());
        Assert.assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
    }

    @Test
    public void testHandlerBD(){
        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController.handlerBD(sqlException, request);
        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StandardErrorHandler());

        Assert.assertEquals(expected.getStatusCode(), result.getStatusCode());
        Assert.assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
    }

    @Test
    public void testHandlerAll(){
        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController.handlerAll(exception, request);
        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StandardErrorHandler());

        Assert.assertEquals(expected.getStatusCode(), result.getStatusCode());
        Assert.assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
    }

    @Test
    public void testHandlerValidacaoJackson(){
        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController.handlerValidacaoJackson(httpMessageNotReadableException, request);
        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardErrorHandler());

        Assert.assertEquals(expected.getStatusCode(), result.getStatusCode());
        Assert.assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
    }

    @Test
    public void testHandlerObjetoNaoEncontrado(){
        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController
                .handlerObjetoNaoEncontrado(objetoNaoEncontradoException, request);

        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardErrorHandler());

        Assert.assertEquals(expected.getStatusCode(), result.getStatusCode());
        Assert.assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
    }

    @Test
    public void testHandlerDestinatarioInvalido(){
        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController.handlerDestinatarioInvalido(destinatarioInvalidoException, request);
        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardErrorHandler());

        Assert.assertEquals(expected.getStatusCode(), result.getStatusCode());
        Assert.assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
    }

    @Test
    public void testHandlerAgendamentoInvalido(){
        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController.handlerAgendamentoInvalido(agendamentoInvalidoException, request);
        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardErrorHandler());

        Assert.assertEquals(expected.getStatusCode(), result.getStatusCode());
        Assert.assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
    }

    @Test
    public void testHandlerResultAgendamentoInvalido(){
        ResponseEntity<StandardErrorHandler> result = exceptionHandlerController.handlerResultAgendamentoInvalido(agendamentoInvalidoException, request);
        ResponseEntity<StandardErrorHandler> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardErrorHandler());

        Assert.assertEquals(expected.getStatusCode(), result.getStatusCode());
        Assert.assertEquals(expected.getStatusCodeValue(), result.getStatusCodeValue());
    }
}
