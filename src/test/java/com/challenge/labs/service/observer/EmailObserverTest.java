package com.challenge.labs.service.observer;

import nl.altindag.log.LogCaptor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.junit.Assert.assertTrue;

public class EmailObserverTest {
    @Mock
    Logger log;
    @InjectMocks
    EmailObserver emailObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testEnviarNotificacao() {
        String expectedInfoMessage = "Enviando mensagem por email";
        LogCaptor logCaptor = LogCaptor.forClass(EmailObserver.class);
        logCaptor.setLogLevelToInfo();
        emailObserver.enviarNotificacao();
        assertTrue(logCaptor.getInfoLogs().contains(expectedInfoMessage));
    }

}
