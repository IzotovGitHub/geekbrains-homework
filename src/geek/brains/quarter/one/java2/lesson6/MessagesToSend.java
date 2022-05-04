package geek.brains.quarter.one.java2.lesson6;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.System.lineSeparator;

public class MessagesToSend {

    private String name;
    private String date;

    private final ConcurrentLinkedQueue<String> messagesQueueToSend = new ConcurrentLinkedQueue<>();
    private final StringBuilder builder = new StringBuilder();

    private static final Format FORMAT = new SimpleDateFormat("dd-MM-yy");
    private static final String COLON = ": ";

    public MessagesToSend(String name) {
        this.name = name;
    }

    public boolean add(String message) {
        return messagesQueueToSend.add(message);
    }

    public String pollMessage() {
        return buildMessage();
    }

    public String getName() {
        return name;
    }

    public boolean isEmpty() {
        return messagesQueueToSend.isEmpty();
    }

    private String buildMessage() {
        builder.delete(0, builder.length());

        String newDate = FORMAT.format(new Date());
        String message = messagesQueueToSend.poll();

        if (!newDate.equals(date)) {
            date = newDate;
            return builder.append(lineSeparator())
                    .append(date)
                    .append(lineSeparator())
                    .append(name)
                    .append(COLON)
                    .append(message)
                    .toString();
        }
        return builder.append(name).append(COLON).append(message).toString();

    }
}
