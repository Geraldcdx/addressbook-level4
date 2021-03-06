package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.testutil.TypicalEvents.HOON;
import static seedu.address.testutil.TypicalEvents.IDA;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EventManager;
import seedu.address.model.ReadOnlyEventManager;

public class XmlEventManagerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlEventManagerStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEventManager_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEventManager(null);
    }

    private java.util.Optional<ReadOnlyEventManager> readEventManager(String filePath) throws Exception {
        return new XmlEManagerStorage(Paths.get(filePath)).readEventManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEventManager("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readEventManager("NotXmlFormatEventManager.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readEventManager_invalidEventEventManager_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readEventManager("invalidEventEventManager.xml");
    }

    @Test
    public void readEventManager_invalidAndValidEventEventManager_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readEventManager("invalidAndValidEventEventManager.xml");
    }

    @Test
    public void readAndSaveEventManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempEventManger.xml");
        EventManager original = getTypicalEventManager();
        XmlEManagerStorage xmlEManagerStorage = new XmlEManagerStorage(filePath);

        //Save in new file and read back
        xmlEManagerStorage.saveEventManager(original, filePath);
        ReadOnlyEventManager readBack = xmlEManagerStorage.readEventManager(filePath).get();
        assertEquals(original, new EventManager(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addEvent(HOON);
        original.removeEvent(ALICE);
        xmlEManagerStorage.saveEventManager(original, filePath);
        readBack = xmlEManagerStorage.readEventManager(filePath).get();
        assertEquals(original, new EventManager(readBack));

        //Save and read without specifying file path
        original.addEvent(IDA);
        xmlEManagerStorage.saveEventManager(original); //file path not specified
        readBack = xmlEManagerStorage.readEventManager().get(); //file path not specified
        assertEquals(original, new EventManager(readBack));

    }

    @Test
    public void saveEventManager_nullEventManager_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEventManager(null, "SomeFile.xml");
    }

    /**
     * Saves {@code eventManager} at the specified {@code filePath}.
     */
    private void saveEventManager(ReadOnlyEventManager eventManager, String filePath) {
        try {
            new XmlEManagerStorage(Paths.get(filePath))
                    .saveEventManager(eventManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEventManager_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEventManager(new EventManager(), null);
    }


}
