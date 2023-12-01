package year2022.day07;

import java.util.ArrayList;
import java.util.List;

public class Folder {
    String name;
    List<File> files = new ArrayList<>();
    List<Folder> subfolders = new ArrayList<>();
    Folder parent;

    public Folder(final String name, final Folder parent) {
        super();
        this.name = name;
        this.parent = parent;
    }

    int getSize() {
        int size = 0;
        for (File file : files) {
            size += file.size;
        }

        for (Folder subfolder : subfolders) {
            size += subfolder.getSize();
        }

        return size;
    }
}
