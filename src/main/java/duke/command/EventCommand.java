package duke.command;

//import exception
import duke.exception.UnexpectedDateTimeFormatException;

//import task
import duke.task.Event;

public class EventCommand extends TaskCommand {
    public static final String COMMAND_WORD = "event";

    public EventCommand(String command, String dateTime) throws UnexpectedDateTimeFormatException {
        super(new Event(command, dateTime));
    }
}
