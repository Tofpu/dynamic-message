package io.tofpu.dynamicmessage;

import io.tofpu.dynamicmessage.holder.MessageHolder;

import java.util.HashMap;
import java.util.Map;

public final class DynamicMessage {
    private static final DynamicMessage INSTANCE = new DynamicMessage();
    private final Map<Class<? extends MessageHolder>, MessageHolder> messageHolderMap;

    public static DynamicMessage getInstance() {
        return INSTANCE;
    }

    private DynamicMessage() {
        this.messageHolderMap = new HashMap<>();
    }

    public <T extends MessageHolder> T as(final Class<T> messageClass) {
        MessageHolder messageHolder = messageHolderMap.get(messageClass);
        if (messageHolder == null) {
            try {
                messageHolderMap.put(messageClass, messageHolder = messageClass.newInstance());
            } catch (final InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
        return messageClass.cast(messageHolder);
    }
}
