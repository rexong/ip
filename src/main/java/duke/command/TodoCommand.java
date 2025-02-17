package duke.command;

//import task
import duke.task.Todo;

/**
 * Represents TodoCommand object to be called when user inputs 'todo'.
 */
public class TodoCommand extends AddCommand {
    public static final String COMMAND_WORD = "todo";

    /**
     * Constructs TodoCommand with description.
     * @param description description of task.
     */
    public TodoCommand(String description) {
        super(new Todo(description));
    }
}
