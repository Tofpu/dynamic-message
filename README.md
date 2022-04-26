# Dynamic Message Library
Have you ever wanted to add a way for the user-end to be able to customize the messages, and also have it look nature in the back-end? This library is just for you!

## Example
```java
public class Example {
    public static class MessageDemo extends MessageHolder {
        public String message = "Hello World";

        public MessageDemo() {
            super(new File("src/test/resources/messages.yml"));
        }
    }

    public void run() {
        // creates an instance of MessageDemo, and stores it to a map for ease of retrieval
        // be aware that this will create a file synchronously
        final MessageDemo demo = DynamicMessage.get().create(MessageDemo.class);

        // you can also retrieve the message class via as(Class) method
        final MessageDemo demo2 = DynamicMessage.get().as(MessageDemo.class);

        // you can grab the message values as usual
        System.out.println(demo.message);
    }
}
```

```yml
# Matches the MessageDemo's message field 
message: Hello World Two
```
