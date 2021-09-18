package com.challenge.labs.service.observer;

import nl.altindag.log.LogCaptor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class PushObserverTest {
    @Mock
    Logger log;
    @InjectMocks
    PushObserver pushObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEnviarNotificacao() throws Exception {
        String expectedInfoMessage = "Enviando mensagem por Push ";
        LogCaptor logCaptor = LogCaptor.forClass(PushObserver.class);
        logCaptor.setLogLevelToInfo();
        pushObserver.enviarNotificacao();
        assertTrue(logCaptor.getInfoLogs().contains(expectedInfoMessage));
    }
}