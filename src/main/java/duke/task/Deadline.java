package duke.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** A task of type deadline. */
public class Deadline extends Task {

    /** The deadline of the task. */
    private final LocalDateTime deadline;

    /**
     * Constructs a Deadline.
     *
     * @param task     The description of the deadline.
     * @param deadline The date of the deadline.
     * @param isDone   The status of the deadline.
     */
    private Deadline(String task, LocalDateTime deadline, boolean isDone) {
        super(task, isDone);
        this.deadline = deadline;
    }

    /**
     * Constructs an unfinished Deadline.
     *
     * @param task     The description of the deadline.
     * @param deadline The date of the deadline.
     */
    public Deadline(String task, LocalDateTime deadline) {
        this(task, deadline, false);
    }

    /**
     * Marks the deadline as done.
     *
     * @return The same deadline with a status of completed.
     */
    @Override
    public Deadline markDone() {
        return new Deadline(task, deadline, true);
    }

    /**
     * Compares with another object.
     *
     * @param o The object compared.
     * @return True if the object compared is a Deadline with the same task and deadline.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof Deadline) {
            Deadline d = (Deadline) o;
            return d.task.equals(this.task) && d.deadline.equals(deadline);
        }

        return false;
    }

    @Override
    public LocalDate getDate() {
        return deadline.toLocalDate();
    }

    /**
     * The format used for saving.
     *
     * @return The String format used for saving.
     */
    @Override
    public String saveFormat() {
        return "D" + super.saveFormat() + String.format("%sT%s",
            deadline.format(SAVE_DATE_FORMAT),
            deadline.format(SAVE_TIME_FORMAT));
    }

    /**
     * The format used to display on a list.
     *
     * @return The String format of a deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)",
            deadline.format(DATE_TIME_FORMAT));
    }
}
