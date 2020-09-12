package duke;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import duke.exception.DeadlineInvalidDate;
import duke.exception.DuplicateTaskException;
import duke.exception.EventInvalidDate;
import duke.exception.InvalidDateException;
import duke.exception.InvalidEndDate;
import duke.exception.InvalidIndexException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

/** Contains the task list. */
public class TaskList {

    /** The task list. */
    private ArrayList<Task> tasks;

    /**
     * Constructs a TaskList.
     *
     * @param taskList The task list containing saved tasks.
     */
    public TaskList(ArrayList<Task> taskList) {
        this.tasks = taskList;
    }

    /** Constructs a TaskList. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Compares two objects.
     *
     * @param o The object to compare.
     * @return True if the objects the same, in other words if the object is a TaskList
     * with a list containing the same tasks.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof TaskList) {
            TaskList t = (TaskList) o;
            return tasks.equals(t.tasks);
        }
        return false;
    }

    /**
     * Processes the list command.
     *
     * @param fullCommand The full command given by the user.
     * @throws InvalidDateException If the command is of the format list [description]
     *                              but the [description] is in not in a valid date format.
     */
    public String processList(String fullCommand) throws InvalidDateException {

        assert fullCommand != null;

        if (fullCommand.trim().equalsIgnoreCase("list")) {
            return printList();
        }

        LocalDate date = Parser.getDateTime(fullCommand.substring(
            "list".length()).trim()).toLocalDate();

        return printList(date);
    }

    /**
     * Prints the tasks in the given task list.
     *
     * @param tasks The task list to be printed.
     * @return A String representation of the list of tasks.
     */
    private String printList(ArrayList<Task> tasks) {
        StringBuilder str = new StringBuilder();
        IntStream.range(0, tasks.size())
            .forEach(i -> str.append(String.format("%d. %s\n", i + 1, tasks.get(i))));

        return str.toString().trim();
    }

    /** Prints the tasks in the list. */
    private String printList() {

        if (tasks.size() == 0) {
            return "You have nothing on your list!";
        }

        return printList(tasks);
    }

    /**
     * An overload of the list command.
     * Prints the tasks occurring on the same date in the list.
     *
     * @param date The date given by the user.
     */
    private String printList(LocalDate date) {

        assert date != null;

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM y");
        StringBuilder str = new StringBuilder();

        int i = 1;
        for (Task task: tasks) {
            if (task.getDate().equals(date)) {
                str.append(String.format("%d. %s\n", i + 1, task));
                i++;
            }
        }

        if (str.length() == 0) {
            return "You have nothing to do on "
                + date.format(dateFormat) + ".";
        }

        return String.format("Here's your list on %s:\n", date.format(dateFormat))
            + str.toString().trim();
    }

    /**
     * Adds a ToDo task to the task list.
     *
     * @param task The description of the ToDo task.
     * @throws DuplicateTaskException If an existing ToDo task is already on the list.
     */
    public void addToDo(String task) throws DuplicateTaskException {

        assert task != null;

        Task toDo = new ToDo(task.trim());
        if (tasks.contains(toDo)) {
            throw new DuplicateTaskException();
        }

        tasks.add(toDo);
    }

    /**
     * Adds an Event to the task list.
     *
     * @param input The description of the Event.
     * @throws DuplicateTaskException If an existing Event with the same description
     *                                and date is already on the list.
     * @throws EventInvalidDate       If the date of the event given is not in a valid date time format.
     */
    public void addEvent(String input) throws DuplicateTaskException, EventInvalidDate, InvalidEndDate {

        assert input != null;

        try {
            String task = input.substring(0, input.indexOf('/')).trim();
            LocalDateTime date = input.contains("to ")
                ? Parser.getDateTime(input.substring(
                    input.indexOf("/at") + "/at ".length(), input.indexOf("to ")))
                : Parser.getDateTime(input.substring(
                    input.indexOf("/at") + "/at ".length()));

            LocalDateTime endDate = null;
            if (input.contains("to ")) {
                String timeFormat = "hh:mm:ss";
                String endDateString = input.substring(input.indexOf("to ") + "to ".length());

                if (endDateString.length() <= timeFormat.length()) {
                    endDate = LocalDateTime.of(date.toLocalDate(), LocalTime.parse(endDateString));
                } else {
                    endDate = Parser.getDateTime(endDateString);
                }

                if (endDate.isBefore(date)) {
                    throw new InvalidEndDate();
                }
            }

            Event event = endDate != null
                ? new Event(task, date, endDate)
                : new Event(task, date);

            if (tasks.contains(event)) {
                throw new DuplicateTaskException();
            }

            tasks.add(event);

        } catch (StringIndexOutOfBoundsException | InvalidDateException e) {
            throw new EventInvalidDate();
        }

    }

    /**
     * Adds a Deadline to the task list.
     *
     * @param input The description of the deadline.
     * @throws DuplicateTaskException If an existing Deadline with the same description
     *                                and date is already on the list.
     * @throws DeadlineInvalidDate    If the date of the deadline given is not in a valid date time format.
     */
    public void addDeadline(String input) throws DuplicateTaskException, DeadlineInvalidDate {

        assert input != null;

        try {

            String task = input.substring(0, input.indexOf('/')).trim();
            LocalDateTime date = Parser.getDateTime(
                input.substring(input.indexOf("/by") + "/by ".length()));

            Deadline deadline = new Deadline(task, date);

            if (tasks.contains(deadline)) {
                throw new DuplicateTaskException();
            } else {
                tasks.add(deadline);
            }

        } catch (StringIndexOutOfBoundsException | InvalidDateException e) {
            throw new DeadlineInvalidDate();
        }
    }

    /**
     * Marks the task with the given index as done.
     *
     * @param taskNumbers The indexes of the tasks to be marked.
     * @throws InvalidIndexException If the taskNumbers < 0 or larger than the size of the taskList.
     */
    public String markDone(Integer... taskNumbers) throws InvalidIndexException {
        try {

            StringBuilder str = new StringBuilder();
            str.append("Nice! I've marked these tasks as done:\n");

            Stream.of(taskNumbers).forEach(taskNo -> {
                tasks.set(taskNo - 1, tasks.get(taskNo - 1).markDone());
                str.append(String.format("%s\n", tasks.get(taskNo - 1)));
            });

            return str.toString().trim();

        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException(tasks.size());
        }
    }

    /**
     * Deletes the task with the given index.
     *
     * @param taskNumbers The numbers of the tasks to be deleted.
     * @throws InvalidIndexException If the taskNumbers < 1 or larger than the size of the taskList.
     */
    public String deleteTasks(Integer... taskNumbers) throws InvalidIndexException {
        try {

            // Check if all taskNumbers within index
            boolean hasInvalidIndex = Stream.of(taskNumbers).anyMatch(
                taskNo -> taskNo < 1 || taskNo > tasks.size());

            if (hasInvalidIndex) {
                throw new InvalidIndexException(tasks.size());
            }

            // Store deleted tasks (to print)
            ArrayList<Task> deletedTasks = Stream.of(taskNumbers)
                .map(taskNo -> tasks.get(taskNo - 1))
                .collect(Collectors.toCollection(ArrayList::new));

            // Delete the tasks
            Stream.of(taskNumbers).forEach(taskNo -> tasks.set(taskNo - 1, null));
            tasks.removeIf(Objects::isNull);

            // List deleted tasks
            StringBuilder str = new StringBuilder();
            str.append("Noted. I've removed these tasks:\n");
            deletedTasks.forEach(deleted -> str.append(String.format("%s\n", deleted)));

            return str.toString().trim();

        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException(tasks.size());
        }
    }

    /**
     * Finds tasks that contains any of the given keywords.
     *
     * @param keywords The keywords to search for in tasks.
     * @return A list of tasks with the given keywords.
     */
    public ArrayList<Task> findTasks(String... keywords) {

        ArrayList<Task> foundTasks = new ArrayList<>();

        boolean containsKeyword;
        for (Task task : tasks) {

            containsKeyword = false;
            for (String keyword: keywords) {
                if (task.getTask().contains(keyword.trim().toLowerCase())) {
                    containsKeyword = true;
                    break;
                }
            }

            if (containsKeyword) {
                foundTasks.add(task);
            }
        }

        return foundTasks;
    }

    /**
     * Prints the list of tasks with the given keyword(s).
     *
     * @param keywords The keywords to search for in tasks.
     * @return A String representation of the matching tasks.
     */
    public String printTasks(String... keywords) {
        ArrayList<Task> foundTasks = findTasks(keywords);
        if (foundTasks.size() == 0) {
            return "You have nothing on your list with the given keyword.";
        }

        return "Here are the tasks with the given keyword:\n" + printList(foundTasks);
    }

    /** Prints the recently added task. */
    public String printNewTask() {
        return String.format("Got it. I've added this task:\n %s\n"
            + "You have %d %s on your list.",
            tasks.get(tasks.size() - 1), tasks.size(), tasks.size() == 1 ? "task" : "tasks");
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
