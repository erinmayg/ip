package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

/** A command to display a list of commands and what it does. */
public class HelpCommand extends Command {

    private static final String COMMAND_LIST =
        "List of commands:\n"
        + "- help: displays the list of commands\n"
        + "- todo: adds a ToDo task\n"
        + "- event: adds an Event\n"
        + "- deadline: adds a Deadline\n"
        + "- find: finds tasks with the given keyword(s)\n"
        + "- list: displays all tasks [with the given date]\n"
        + "- done: marks the task(s) with the given index(es) as done\n"
        + "- delete: deletes the task(s) with the given index(es)\n"
        + "- bye: terminates the duke bot\n";

    /**
     * Executes the command by printing the list of commands.
     *
     * @param taskList The task list that stores and modifies the list of saved tasks.
     * @param ui       The UI of the bot.
     * @param storage  The storage system of the bot.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        return COMMAND_LIST + "\n";
    }
}
