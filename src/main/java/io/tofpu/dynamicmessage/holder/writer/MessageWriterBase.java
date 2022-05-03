package io.tofpu.dynamicmessage.holder.writer;

import io.tofpu.dynamicmessage.holder.MessageHolder;
import io.tofpu.dynamicmessage.holder.meta.SkipMessage;
import io.tofpu.dynamicmessage.util.ReflectionUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class MessageWriterBase {
    public abstract void writeToFile(final MessageHolder holder);

    public abstract void readFromFile(final MessageHolder holder);

    public boolean isSkippable(final Field field) {
        return field.isAnnotationPresent(SkipMessage.class);
    }

    public static class DefaultMessageWriter extends MessageWriterBase {
        @Override
        public void writeToFile(final MessageHolder holder) {
            final Map<String, String> messages = new HashMap<>();
            for (final Field field : holder.getCachedFields()) {
                if (isSkippable(field)) {
                    continue;
                }

                if (field.getType() != String.class) {
                    throw new IllegalStateException(
                            "Field " + field.getName() + " is not of type String");
                }

                messages.put(field.getName(), ReflectionUtils.readField(holder, field));
            }

            final StringBuilder builder = new StringBuilder();
            for (final Map.Entry<String, String> entry : messages.entrySet()) {
                builder.append(entry.getKey()
                                .replace(" ", "_"))
                        .append(": ")
                        .append(entry.getValue().replace("\n", "\\n"))
                        .append("\n");
            }

            try (final FileWriter writer = new FileWriter(holder.getMessageFile())) {
                writer.write(builder.substring(0, builder.length() - 1));
            } catch (final IOException e) {
                throw new IllegalStateException("Could not write to file at " +
                                                holder.getMessageFile()
                                                        .getAbsolutePath(), e);
            }
        }

        @Override
        public void readFromFile(final MessageHolder holder) {
            try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(holder.getMessageFile()), StandardCharsets.UTF_8))) {
                while (reader.ready()) {
                    final String line = reader.readLine();
                    final String[] split = line.split(": ");

                    final String key = split[0].replace("_", " ");
                    final String value = substring(split, 1).replaceFirst(" ", "")
                            .replace("\\n", "\n");

                    for (final Field field : holder.getCachedFields()) {
                        if (!field.getName()
                                .equals(key)) {
                            continue;
                        }

                        if (field.getType() != String.class) {
                            throw new IllegalStateException("Field " + field.getName() +
                                                            " is not of type String");
                        }

                        ReflectionUtils.setField(holder, field, value);
                    }
                }
            } catch (final IOException e) {
                throw new IllegalStateException("Could not find file at " +
                                                holder.getMessageFile()
                                                        .getAbsolutePath(), e);
            }
        }


        private String substring(final String[] array, final int startIndex) {
            final StringBuilder builder = new StringBuilder();
            for (int i = startIndex; i < array.length; i++) {
                if (i != startIndex) {
                    builder.append(":");
                }
                builder.append(array[i]);
            }
            return builder.toString();
        }
    }
}
