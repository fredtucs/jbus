/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.jbus;

import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mi-PC
 */
public class JBusTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private JBus jBus;

    @Before
    public void setUp() {
        jBus = new JBus();
    }

    @Test
    public void testSubscription() {
        JBusTest.Listener listener = new JBusTest.Listener();
        JBusTest.ListenerTwo listenertwo = new JBusTest.ListenerTwo();
        jBus.register(listener);
        jBus.register(listenertwo);
        jBus.post(new JBusTest.Event());
        jBus.post(new JBusTest.Event(), ListenerTwo.class);

        assertEquals(listener.listen1Counter.intValue(), 1);
        assertEquals(listenertwo.listen1Counter.intValue(), 2);
        
        jBus.deregister(listener);
        jBus.post(new JBusTest.Event());

        assertEquals(listener.listen1Counter.intValue(), 1);
    }

    private class Event {
    }

    private class Listener {

        AtomicInteger listen1Counter = new AtomicInteger(0);

        @Subscribe
        public void listen1(Event event) {
            logger.debug("Listener Event execute");
            listen1Counter.getAndIncrement();
        }

    }

    private class ListenerTwo {

        AtomicInteger listen1Counter = new AtomicInteger(0);

        @Subscribe
        public void listen1(Event event) {
            logger.debug("ListenerTwo Event execute");
            listen1Counter.getAndIncrement();
        }

    }

}
