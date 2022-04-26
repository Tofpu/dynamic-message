package io.tofpu.dynamicmessage;

import io.tofpu.dynamicmessage.holder.MessageHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DynamicMessage {
    private static final DynamicMessage INSTANCE = new DynamicMessage();

    /**
     * Holds all the message holders.
     */
    private final Map<Class<? extends MessageHolder>, MessageHolder> messageHolderMap;

    /**
     * @return a singleton instance of {@link DynamicMessage}.
     */
    public static DynamicMessage get() {
        return INSTANCE;
    }

    private DynamicMessage() {
        this.messageHolderMap = new ConcurrentHashMap<>();
    }

    /**
     * This method is used to load a MessageHolder.
     *
     * @param messageClass the message class
     * @param <T> the message class
     *
     * @return the message holder
     * @throws IllegalStateException if the message holder already exists
     * @throws IllegalArgumentException if the message holder cannot be loaded
     */
    public <T extends MessageHolder> T create(Class<T> messageClass) {
        if (messageHolderMap.containsKey(messageClass)) {
            throw new IllegalStateException("MessageHolder already exists");
        }

        final T value;
        try {
            value = messageClass.newInstance();
            messageHolderMap.put(messageClass, value);

            value.constructFile();
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(messageClass.getSimpleName() + " cannot be loaded", e);
        }

        return value;
    }

    /**
     * Returns the message holder.
     *
     * @param messageClass the message class
     * @param <T> the message class
     *
     * @return the message holder
     * @throws IllegalStateException if the message holder does not exist
     */
    public <T extends MessageHolder> T as(final Class<T> messageClass) {
        final MessageHolder messageHolder = messageHolderMap.get(messageClass);
        if (messageHolder == null) {
            throw new IllegalStateException(
                    "MessageHolder type " + messageClass.getSimpleName() + "not found");
        }
        return messageClass.cast(messageHolder);
    }

    /**
     * @param messageClass the message class
     * @param <T> the message class
     *
     * @return the removed message holder
     * @throws IllegalStateException if the message holder does not exist
     */
    public <T extends MessageHolder> T unload(final Class<T> messageClass) {
        final MessageHolder messageHolder = messageHolderMap.remove(messageClass);
        if (messageHolder == null) {
            throw new IllegalStateException(
                    "MessageHolder type " + messageClass.getSimpleName() + "not found");
        }
        return messageClass.cast(messageHolder);
    }
}
