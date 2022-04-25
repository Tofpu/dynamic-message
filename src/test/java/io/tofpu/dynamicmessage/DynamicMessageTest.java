package io.tofpu.dynamicmessage;

import io.tofpu.dynamicmessage.holder.MessageHolder;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicMessageTest {
    public static class MessageDemo extends MessageHolder {
        public String message = "Hello World";

        public MessageDemo() {
            super(new File("src/test/resources/messages.yml"));
        }
    }

    @Test
    public void test_message() {
        final MessageDemo demo = DynamicMessage.get().as(MessageDemo.class);
        demo.constructFile();

        assertEquals("Hello World Two", demo.message);
    }
}
