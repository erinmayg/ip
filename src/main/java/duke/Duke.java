package duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.gui.Message;

/** The duke bot that stores your tasks. */
public class Duke {

    /** Deals with interaction with the user. */
    private final Ui ui;
    /** Deals with saving and loading the tasks from the file. */
    private final Storage storage;
    /** Contains the task list and add/delete operations for the task list. */
    private TaskList tasks;

    /**
     * Constructs a Duke bot.
     *
     * @param filePath The filepath to store the data in.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /** Constructs a Duke bot with the default save location (data/duke.txt). */
    public Duke() {
        this("data");
    }

    /** Runs the bot, accepts tasks and saves them into the file. */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                ui.printMessage(c.execute(tasks, ui, storage));
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /** Runs the program. */
    public static void main(String[] args) {
        Duke bot = new Duke("data");
        bot.run();
    }

    /**
     * Obtains the Duke bot's response to the user's input.
     *
     * @param input The user's input
     * @return The Duke bot's response
     */
    public Message getResponse(String input) {
        assert input != null;

        try {
            Command c = Parser.parse(input);
            return new Message(c.execute(tasks, ui, storage));
        } catch (DukeException e) {
            return new Message(e.getMessage(), true);
        }
    }
}
