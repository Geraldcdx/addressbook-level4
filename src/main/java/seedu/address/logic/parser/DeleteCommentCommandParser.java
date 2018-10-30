//@@author Geraldcdx
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeleteCommentCommand.MESSAGE_LINE_STRING_INVALID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class DeleteCommentCommandParser implements Parser<DeleteCommentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LINE);

        int line;
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommentCommand.MESSAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_LINE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommentCommand.MESSAGE));
        }
        try {
            line = ParserUtil.parseLine(argMultimap.getValue(PREFIX_LINE).get());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_LINE_STRING_INVALID);
        }

        return new DeleteCommentCommand(index , line);
    }
}
