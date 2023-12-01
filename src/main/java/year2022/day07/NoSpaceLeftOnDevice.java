package year2022.day07;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.Puzzle;

public class NoSpaceLeftOnDevice extends Puzzle {

    List<Folder> folders = new ArrayList<>();

    protected NoSpaceLeftOnDevice(final String input) throws IOException {
        super(input);
        Pattern cdPattern = Pattern.compile("\\$ cd ([\\w/]+)");
        Pattern filePattern = Pattern.compile("(\\d+) ([\\w\\.]+)");

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;
            Folder current = null;

            while ((line = br.readLine()) != null) {
                Matcher cdMatcher = cdPattern.matcher(line);
                if (cdMatcher.find()) {
                    Folder f = new Folder(cdMatcher.group(1), current);
                    folders.add(f);
                    if (current != null) {
                        current.subfolders.add(f);
                    }
                    current = f;
                    continue;
                }

                if (line.equals("$ cd ..")) {
                    current = current.parent;
                    continue;
                }

                Matcher fileMatcher = filePattern.matcher(line);
                if (fileMatcher.find()) {
                    current.files.add(new File(Integer.parseInt(fileMatcher.group(1)), fileMatcher.group(2)));
                    continue;
                }
            }
        }
    }

    public int count() {
        int result = 0;
        for (Folder folder : folders) {
            int size = folder.getSize();
            if (size <= 100000) {
                result += size;
            }
        }
        return result;
    }

    public int findFolderToDelete() {
        int currentSize = folders.get(0).getSize();
        int currentFree = 70000000 - currentSize;
        int toFree = 30000000 - currentFree;
        int min = 70000000;
        for (Folder folder : folders) {

            int size = folder.getSize();
            if (size >= toFree && size < min) {
                min = size;
            }
        }
        return min;
    }

    @Override
    public Object getAnswer1() {
        return count();
    }

    @Override
    public Object getAnswer2() {
        return findFolderToDelete();
    }

    public static void main(final String[] args) throws IOException {
        NoSpaceLeftOnDevice noSpaceLeftOnDevice = new NoSpaceLeftOnDevice("year2022/day07/input");
        System.out.println("Answer1: " + noSpaceLeftOnDevice.getAnswer1());
        System.out.println("Answer2: " + noSpaceLeftOnDevice.getAnswer2());
    }


}
