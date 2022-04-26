package io.tofpu.dynamicmessage;

import io.tofpu.dynamicmessage.holder.MessageHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DynamicMessageTest {
    public static class MessageDemo extends MessageHolder {
        public String message = "Hello World";

        public MessageDemo() {
            super(new File("src/test/resources/messages.yml"));
        }
    }

    @Test
    @Order(1)
    public void message_creation() {
        final MessageDemo demo = DynamicMessage.get().create(MessageDemo.class);

        assertNotNull(demo, "MessageDemo should not be null");
    }

    @Test
    @Order(2)
    public void message_retrieval() {
        final MessageDemo demo = DynamicMessage.get().as(MessageDemo.class);

        assertNotNull(demo, "MessageDemo should not be null");
    }

    @Test
    @Order(3)
    public void message_compare_initial() {
        final MessageDemo demo = DynamicMessage.get().as(MessageDemo.class);

        assertEquals("Hello World", demo.message);
    }

    @Test
    @Order(4)
    public void message_compare_update_one() {
        final MessageDemo demo = DynamicMessage.get().as(MessageDemo.class);

        demo.message = "Hello World Updated";
        demo.save();

        DynamicMessage.get().unload(MessageDemo.class);
    }

    @Test
    @Order(5)
    public void message_compare_update_two() {
        final MessageDemo demo = DynamicMessage.get().create(MessageDemo.class);

        assertEquals("Hello World Updated", demo.message);
    }

    @AfterAll
    public static void tearDown() {
        DynamicMessage.get().as(MessageDemo.class).delete();
    }
}
