package io.tofpu.dynamicmessage;

import io.tofpu.dynamicmessage.holder.MessageHolder;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicMessageTest {
    public static class DynamicClassDemo extends MessageHolder {
        public String message = "Hello World";

        public DynamicClassDemo() {
            super(new File("src/test/resources/messages.yml"));
        }
    }

    @Test
    public void testDynamicMessage() {
        final DynamicClassDemo demo = DynamicMessage.getInstance().as(DynamicClassDemo.class);
        demo.constructFile();

        assertEquals("Hello World Two", demo.message);
    }
}
