package duke.gui;

/** The Duke bot's response to the user input. */
public class Message {

    /** The duke bot's response. */
    private String text;

    /** Determines if the response is an error message. */
    private boolean isError;

    /**
     * Constructs a message.
     *
     * @param text The content of the message.
     * @param isError The status of the message.
     */
    public Message(String text, boolean isError) {
        this.text = text;
        this.isError = isError;
    }

    /**
     * Constructs a regular message (not an error message).
     *
     * @param text The content of the message.
     */
    public Message(String text) {
        this(text, false);
    }

    /**
     * Obtains the message's content.
     *
     * @return The content of the message.
     */
    public String getText() {
        return text;
    }

    /**
     * Obtains the message's status.
     *
     * @return True if the message is an error message, otherwise it will return false.
     */
    public boolean isError() {
        return isError;
    }
}
