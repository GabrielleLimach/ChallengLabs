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

public class WhatsapObserverTest {
    @Mock
    Logger log;
    @InjectMocks
    WhatsapObserver whatsapObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEnviarNotificacao(){
        String expectedInfoMessage = "Enviando mensagem por Whatsap ";
        LogCaptor logCaptor = LogCaptor.forClass(WhatsapObserver.class);
        logCaptor.setLogLevelToInfo();
        whatsapObserver.enviarNotificacao();
        assertTrue(logCaptor.getInfoLogs().contains(expectedInfoMessage));
    }
}