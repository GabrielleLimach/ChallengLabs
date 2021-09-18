package com.challenge.labs.service.observer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

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
    @Ignore
    public void testEnviarNotificacao() throws Exception {
        emailObserver.enviarNotificacao();
//        Assert.assertEquals();
    }
}
