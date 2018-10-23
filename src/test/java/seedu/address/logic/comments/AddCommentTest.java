package seedu.address.logic.comments;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AddCommentTest {
    private String comments =
            "<span>Comment Section</span>\n"
                    + "<ol>\n"
                    + "<li>hello</li>\n"
                    + "<li>My name is Gerald</li>\n"
                    + "<li>Yup</li>\n"
                    + "<li>REPLY--->HArlo</li>\n"
                    + "<li>REPLY--->my name is</li>\n"
                    + "<li>REPLY--->Chua</li>\n"
                    + "<li>hello</li>\n"
                    + "<li>there</li>\n"
                    + "<li>hello</li>\n"
                    + "</ol>";

    private String testcase =
             "<span>Comment Section</span>\n"
                     + "<ol>\n"
                     + "<li>hello</li>\n"
                     + "<li>My name is Gerald</li>\n"
                     + "<li>Yup</li>\n"
                     + "<li>REPLY--->HArlo</li>\n"
                     + "<li>REPLY--->my name is</li>\n"
                     + "<li>REPLY--->Chua</li>\n"
                     + "<li>hello</li>\n"
                     + "<li>there</li>\n"
                     + "<li>hello</li>\n"
                     + "<li>hi</li>\n"
                     + "</ol>";
    @Test
    void addComment_correctInput_success() {
        AddComment test = new AddComment(comments);
        assertEquals(testcase, test.addComment("hi"));
    }
    @Test
    void addComment_incorrectInput_failure() {
        AddComment test = new AddComment(comments);
        assertNotEquals(testcase, test.addComment("i"));
    }

    @Test
    void getInput_correctInput_success() {
        AddComment test = new AddComment(comments);
        assertEquals(comments, test.getInput());
    }
}