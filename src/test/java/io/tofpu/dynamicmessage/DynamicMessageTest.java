package io.tofpu.dynamicmessage;

import io.tofpu.dynamicmessage.holder.MessageHolder;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DynamicMessageTest {
    public static class MessageDemo extends MessageHolder {
        public String message = "Hello World";

        public MessageDemo() {
            super(new File("src/test/resources/messages.yml"));
        }
    }

    @Test
    public void message_creation() {
        final MessageDemo demo = DynamicMessage.get().create(MessageDemo.class);

        assertNotNull(demo, "MessageDemo should not be null");
    }

    @Test
    public void message_retrieval() {
        final MessageDemo demo = DynamicMessage.get().as(MessageDemo.class);

        assertNotNull(demo, "MessageDemo should not be null");
    }

    @Test
    public void message_file_construction() {
        final MessageDemo demo = DynamicMessage.get().as(MessageDemo.class);
        demo.constructFile();

        assertEquals("Hello World Two", demo.message);
    }
}
