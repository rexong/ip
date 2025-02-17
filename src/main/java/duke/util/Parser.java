package duke.util;

//import command
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeadlineCommand;
import duke.command.DeleteCommand;
import duke.command.EventCommand;
import duke.command.FindCommand;
import duke.command.InvalidCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.TodoCommand;
import duke.command.UnmarkCommand;
//import exception
import duke.exception.DateTimeNotFoundException;
import duke.exception.DescriptionNotSpecifyException;
import duke.exception.DukeException;
import duke.exception.IndexNotSpecifyException;
import duke.exception.IntegerExpectedException;
import duke.exception.NoDescriptionException;
import duke.exception.ScheduleTaskKeywordNotFoundException;
import duke.exception.UnexpectedDateTimeFormatException;

/**
 * Handles the command given by the user.
 */
public class Parser {
    /**
     * Parses the command given by the user.
     * Return a command based on the user's input.
     *
     * @param command user's input.
     * @return Command object based on the user's input.
     * @throws DukeException when there is an error in the user's input.
     * @throws NumberFormatException when integer is expected but not given.
     */
    public static Command parse(String command) throws DukeException, NumberFormatException {
        String commandWord = getCommandWord(command);
        String description = getDescription(command);
        switch (commandWord) {
        case ListCommand.COMMAND_WORD:
            return prepareList(commandWord, description);
        case ByeCommand.COMMAND_WORD:
            return prepareBye(commandWord, description);
        case MarkCommand.COMMAND_WORD:
            return prepareMark(commandWord, description);
        case UnmarkCommand.COMMAND_WORD:
            return prepareUnmark(commandWord, description);
        case TodoCommand.COMMAND_WORD:
            return prepareTodo(commandWord, description);
        case DeadlineCommand.COMMAND_WORD:
            return prepareDeadline(commandWord, description);
        case EventCommand.COMMAND_WORD:
            return prepareEvent(commandWord, description);
        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(commandWord, description);
        case FindCommand.COMMAND_WORD:
            return prepareFind(commandWord, description);
        default:
            return prepareInvalid();
        }
    }

    private static String getCommandWord(String command) {
        if (command.startsWith("list")) {
            return "list";
        }
        if (command.startsWith("bye")) {
            return "bye";
        }
        if (command.startsWith("mark")) {
            return "mark";
        }
        if (command.startsWith("unmark")) {
            return "unmark";
        }
        if (command.startsWith("todo")) {
            return "todo";
        }
        if (command.startsWith("deadline")) {
            return "deadline";
        }
        if (command.startsWith("event")) {
            return "event";
        }
        if (command.startsWith("delete")) {
            return "delete";
        }
        if (command.startsWith("find")) {
            return "find";
        }
        return "invalid";
    }

    private static String getDescription(String command) {
        int indexOfFirstWhiteSpace = command.indexOf(' ');
        return (indexOfFirstWhiteSpace != -1)
                ? command.substring(indexOfFirstWhiteSpace + 1)
                : "";
    }

    private static int convertStringToInt(String number) throws IntegerExpectedException {
        try {
            int index = Integer.parseInt(number) - 1;
            return index;
        } catch (NumberFormatException e) {
            throw new IntegerExpectedException();
        }

    }

    private static String[] splitBy(String command, String description, String splitCommand)
            throws DateTimeNotFoundException, DescriptionNotSpecifyException {
        String[] descriptions = description.split(splitCommand);
        if (descriptions.length == 1) {
            throw new DateTimeNotFoundException(command, splitCommand);
        }
        if (descriptions.length > 1 && descriptions[0].strip() == "") {
            throw new DescriptionNotSpecifyException(command);
        }
        return descriptions;
    }

    private static Command prepareList(String command, String description) throws NoDescriptionException {
        if (!description.equals("")) {
            throw new NoDescriptionException(command);
        }
        return new ListCommand();
    }

    private static Command prepareBye(String command, String description) throws NoDescriptionException {
        if (!description.equals("")) {
            throw new NoDescriptionException(command);
        }
        return new ByeCommand();
    }

    private static Command prepareMark(String command, String description)
            throws IndexNotSpecifyException, IntegerExpectedException {
        if (description.equals("")) {
            throw new IndexNotSpecifyException(command);
        }
        return new MarkCommand(convertStringToInt(description));
    }

    private static Command prepareUnmark(String command, String description)
            throws IndexNotSpecifyException, IntegerExpectedException {
        if (description.equals("")) {
            throw new IndexNotSpecifyException(command);
        }
        return new UnmarkCommand(convertStringToInt(description));
    }

    private static Command prepareTodo(String command, String description) throws DescriptionNotSpecifyException {
        if (description.equals("")) {
            throw new DescriptionNotSpecifyException(command);
        }
        return new TodoCommand(description);
    }

    private static Command prepareDeadline(String command, String description)
            throws DescriptionNotSpecifyException, ScheduleTaskKeywordNotFoundException,
            DateTimeNotFoundException, UnexpectedDateTimeFormatException {
        if (description.equals("")) {
            throw new DescriptionNotSpecifyException(command);
        }
        if (!description.contains("/by")) {
            throw new ScheduleTaskKeywordNotFoundException(command, "/by");
        }
        String[] descriptions = splitBy(command, description, "/by");
        return new DeadlineCommand(descriptions[0].strip(), descriptions[1].strip());
    }

    private static Command prepareEvent(String command, String description)
            throws DescriptionNotSpecifyException, ScheduleTaskKeywordNotFoundException,
            DateTimeNotFoundException, UnexpectedDateTimeFormatException {
        if (description.equals("")) {
            throw new DescriptionNotSpecifyException(command);
        }
        if (!description.contains("/at")) {
            throw new ScheduleTaskKeywordNotFoundException(command, "/at");
        }
        String[] descriptions = splitBy(command, description, " /at ");
        return new EventCommand(descriptions[0].strip(), descriptions[1].strip());
    }

    private static Command prepareDelete(String command, String description)
            throws IndexNotSpecifyException, IntegerExpectedException {
        if (description.equals("")) {
            throw new IndexNotSpecifyException(command);
        }
        return new DeleteCommand(convertStringToInt(description));
    }

    private static Command prepareFind(String command, String description) throws DescriptionNotSpecifyException {
        if (description.equals("")) {
            throw new DescriptionNotSpecifyException(command);
        }
        return new FindCommand(description);
    }

    private static Command prepareInvalid() {
        return new InvalidCommand();
    }
}
