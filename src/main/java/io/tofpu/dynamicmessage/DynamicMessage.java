package io.tofpu.dynamicmessage;

import io.tofpu.dynamicmessage.holder.MessageHolder;

import java.util.HashMap;
import java.util.Map;

public final class DynamicMessage {
    private static final DynamicMessage INSTANCE = new DynamicMessage();
    private final Map<Class<? extends MessageHolder>, MessageHolder> messageHolderMap;

    public static DynamicMessage get() {
        return INSTANCE;
    }

    private DynamicMessage() {
        this.messageHolderMap = new HashMap<>();
    }

    public <T extends MessageHolder> T create(Class<T> messageClass) {
        if (messageHolderMap.containsKey(messageClass)) {
            throw new IllegalStateException("MessageHolder already exists");
        }

        final T value;
        try {
            value = messageClass.newInstance();
            messageHolderMap.put(messageClass, value);
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        return value;
    }

    public <T extends MessageHolder> T as(final Class<T> messageClass) {
        final MessageHolder messageHolder = messageHolderMap.get(messageClass);
        if (messageHolder == null) {
            throw new IllegalStateException(
                    "MessageHolder type " + messageClass.getSimpleName() + "not found");
        }
        return messageClass.cast(messageHolder);
    }
}
