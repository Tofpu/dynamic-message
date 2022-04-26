package io.tofpu.dynamicmessage.holder;

import io.tofpu.dynamicmessage.holder.writer.MessageWriter;

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

    /**
     * Constructs the file that will be used to store/read the message
     */
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

    /**
     * Delete the message file
     */
    public void delete() {
        messageFile.delete();
    }

    /**
     * Populates the fields with the values from the file
     */
    public void reload() {
        messageWriter.readFromFile(this);
    }

    /**
     * Dumps the field values to the file
     */
    public void save() {
        messageWriter.writeToFile(this);
    }

    /**
     * This method constructs the message writer, which is used to write and read the
     * message file. It's recommended to override this method if you want to use your own
     * writer.
     *
     * @return the messageWriter to be used
     */
    protected MessageWriter constructMessageWriter() {
        return new MessageWriter.DefaultMessageWriter();
    }

    /**
     * @return the file where the message is stored
     */
    public File getMessageFile() {
        return messageFile;
    }

    /**
     * @return the cachedFields
     */
    public Field[] getCachedFields() {
        return cachedFields;
    }
}
