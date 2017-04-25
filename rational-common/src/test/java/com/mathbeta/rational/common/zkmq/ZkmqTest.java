package com.mathbeta.rational.common.zkmq;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 17-4-23.
 */
public class ZkmqTest {
    private Client client;
    private Server server;

    @Before
    public void before() {
        server = new Server("127.0.0.1", "zkmq");
        server.start();
        server.listen("myqueue", new IListener() {
            @Override
            public byte[] handle(String messageId, byte[] message) throws Exception {
                System.out.println("received message: id=" + messageId + ", body=" + new String(message));

                return ("this is the response for message [" + messageId + "]").getBytes();
            }
        });


        client = new Client("127.0.0.1", "zkmq");
        client.start();
    }

    @After
    public void after() {
        client.close();
        server.removeListener("myqueue");
        server.close();
    }

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            String message = "hello, I am Jack, my no. is " + i;
            byte[] response = client.sendAndReceive("myqueue", message.getBytes());
            System.out.println("response is :" + new String(response));
        }

        System.out.println("sending 10000 messages using time: " + (System.currentTimeMillis() - start) + "ms");
    }
}
