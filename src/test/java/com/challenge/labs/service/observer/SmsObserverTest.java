package com.challenge.labs.service.observer;

import nl.altindag.log.LogCaptor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.junit.Assert.assertTrue;

public class SmsObserverTest {
    @Mock
    Logger log;
    @InjectMocks
    SmsObserver smsObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEnviarNotificacao(){
        String expectedInfoMessage = "Enviando mensagem SMS ";
        LogCaptor logCaptor = LogCaptor.forClass(SmsObserver.class);
        logCaptor.setLogLevelToInfo();
        smsObserver.enviarNotificacao();
        assertTrue(logCaptor.getInfoLogs().contains(expectedInfoMessage));

    }
}