package helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public abstract class Puzzle {
    private final File inputFile;
    private List<String> cachedLines = null;

    protected Puzzle(final String input) {
        this.inputFile = new ResourceReader().read(input);
    }

    protected File getInputFile() {
        return inputFile;
    }

    protected List<String> getLines() throws IOException {
        if (cachedLines == null) {
            cachedLines = Files.readAllLines(inputFile.toPath());
        }
        return cachedLines;
    }

    public abstract Object getAnswer1();

    public abstract Object getAnswer2();
}