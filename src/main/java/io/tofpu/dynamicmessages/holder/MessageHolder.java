package io.tofpu.dynamicmessages.holder;

import io.tofpu.dynamicmessages.holder.writer.MessageWriter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class MessageHolder {
    private final Field[] cachedFields;
    private final File messageFile;
    private final MessageWriter messageWriter;

    protected MessageHolder(final File messageFile) {
        this.cachedFields = this.getClass().getDeclaredFields();
        this.messageFile = messageFile;
        this.messageWriter = constructMessageWriter();
    }

    public void constructFile() {
        if (!messageFile.exists()) {
            messageFile.getParentFile()
                    .mkdirs();
            try {
                messageFile.createNewFile();
            } catch (IOException e) {
                throw new IllegalStateException(
                        "Could not create file at " + messageFile.getAbsolutePath(), e);
            }

            messageWriter.writeToFile(this);
            return;
        }

        messageWriter.readFromFile(this);
    }

    protected MessageWriter constructMessageWriter() {
        return new MessageWriter.DefaultMessageWriter();
    }

    public File getMessageFile() {
        return messageFile;
    }

    public Field[] getCachedFields() {
        return cachedFields;
    }
}
