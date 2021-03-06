package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.User;

//@@ jamesyaputra
/**
 * Logs user into the Event Manager Application
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs user into Event Manager. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD";

    public static final String MESSAGE_SUCCESS = "Logged in: %1$s";
    public static final String MESSAGE_LOGGED = "Already logged in as %1$s!";
    public static final String MESSAGE_FAILURE = "Incorrect account credentials!";

    private final User toLogin;

    /**
     * Creates an LoginCommand to add the specified {@code User}
     */
    public LoginCommand(User user) {
        requireNonNull(user);
        toLogin = user;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getLoginStatus()) {
            throw new CommandException(String.format(MESSAGE_LOGGED, model.getUsername().toString()));
        }

        model.logUser(toLogin);
        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toLogin.getUsername().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && toLogin.equals(((LoginCommand) other).toLogin));
    }
}
