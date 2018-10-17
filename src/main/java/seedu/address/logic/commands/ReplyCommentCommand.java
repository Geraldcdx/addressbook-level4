package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.comments.ReplyComment;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Replies a comment in the comment section of the event
 */
public class ReplyCommentCommand extends Command {

    public static final String COMMAND_WORD = "replyComment";

    public static final String MESSAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_LINE + "LINE] "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINE + "91 "
            + PREFIX_COMMENT + "johndoe@example.com is here";

    public static final String MESSAGE_REPLY_COMMENT = "Comment [%1$s] replied for Event %2$s at Line %3$s";

    private final Index index;
    private final EditCommand.EditPersonDescriptor editCommentDescriptor;
    private int line = 0;
    private String comment = null;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editPersonDescriptor details to edit the event with
     */
    public ReplyCommentCommand(Index index, int line, String comment) {
        requireNonNull(index);
        requireNonNull(line);
        requireNonNull(comment);

        this.index = index;
        this.line = line;
        this.editCommentDescriptor = new EditCommand.EditPersonDescriptor();
    }

    public String getComment() {
        return this.comment;
    }

    public int getLine() {
        return this.line;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> filteredEventList = model.getFilteredEventList();

        if (index.getZeroBased() >= filteredEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        String test =
                "<span>Comment Section</span>\n"
                        + "<ol>\n"
                        + "<li>hello</li>\n"
                        + "<li>My name is Gerald</li>\n"
                        + "<li>What is your name?</li>\n"
                        + "</ol>";

        ReplyComment comments = new ReplyComment(test);
        test = comments.replyComment(getComment(), getLine());

        File savingFile = new File("C:/Users/Gerald/Desktop/test/1.html");
        FileOutputStream fop = null;
        try {
            fop = new FileOutputStream(savingFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fop.write(test.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));

        Event eventToEdit = filteredEventList.get(index.getZeroBased());
        Event editedEvent = EditCommand.createEditedPerson(eventToEdit, editCommentDescriptor);

        model.updateEvent(eventToEdit, editedEvent);
        model.commitEventManager();

        return new CommandResult(String.format(MESSAGE_REPLY_COMMENT, getComment(), index.getOneBased(), getLine()));
    }

}
