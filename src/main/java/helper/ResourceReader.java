package helper;

import java.io.File;
import java.net.URL;

public class ResourceReader {
    public File read(final String input) {
        URL resource = getClass().getClassLoader().getResource(input);
        if (resource == null) {
            throw new IllegalArgumentException("file <" + input + "> not found!");
        } else {
            return new File(resource.getFile());
        }
    }
}
