package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

public class ExitCommand extends Command {

    public ExitCommand() {
        isExit = true;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.bye();
    }
}