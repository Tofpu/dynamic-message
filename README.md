# Dynamic Message Library
Have you ever wanted to add a way for the user-end to be able to customize the messages, and also have it look nature in the back-end? This library is just for you!

## Example
```java
public class DynamicMessageTest {
    public static class MessageDemo extends MessageHolder {
        public String message = "Hello World";

        public MessageDemo() {
            super(new File("src/test/resources/messages.yml"));
        }
    }

    public void testDynamicMessage() {
        // retrieving an instance of MessageDemo
        final MessageDemo messageDemo = DynamicMessage.get().as(MessageDemo.class);
        // constructing the file
        messageDemo.constructFile();

        assertEquals("Hello World Two", demo.messageDemo);
    }
}
```

```yml
# Matches the MessageDemo's message field 
message: Hello World Two
```
