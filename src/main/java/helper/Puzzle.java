package helper;

import java.io.File;
import java.io.IOException;

public abstract class Puzzle {
    File inputFile;

    protected Puzzle(final String input) throws IOException {
        super();
        this.inputFile = new ResourceReader().read(input);
    }

    protected File getInputFile() {
        return inputFile;
    }

    public abstract Object getAnswer1();

    public abstract Object getAnswer2();

}
