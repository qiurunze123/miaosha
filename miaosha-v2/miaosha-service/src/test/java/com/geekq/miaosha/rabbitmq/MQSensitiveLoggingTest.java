package com.geekq.miaosha.rabbitmq;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class MQSensitiveLoggingTest {

    @Test
    public void senderAndReceiverDoNotLogSerializedMessageBodies() throws Exception {
        String sender = source("MQSender.java");
        String receiver = source("MQReceiver.java");

        assertFalse(sender.contains("log.info(\"send message:\" + msg)"));
        assertFalse(sender.contains("log.info(\"send message:{}\", msg)"));
        assertFalse(receiver.contains("log.info(\"receive message:\" + message)"));
    }

    private String source(String name) throws Exception {
        return new String(Files.readAllBytes(Paths.get(
                "src/main/java/com/geekq/miaosha/rabbitmq/" + name)), StandardCharsets.UTF_8);
    }
}
