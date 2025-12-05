package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PuzzleGenerator {

    public static void createNewPuzzle(int year, int day, String puzzleName) throws IOException {
        String dayStr = String.format("day%02d", day);
        String pkg = "year" + year + "." + dayStr;

        // Maven-style source folders
        String srcMainJava = "src/main/java/" + pkg.replace('.', '/');
        String srcTestJava = "src/test/java/" + pkg.replace('.', '/');
        String resFolder = "resources/" + pkg.replace('.', '/');

        // Create folders
        new File(srcMainJava).mkdirs();
        new File(srcTestJava).mkdirs();
        new File(resFolder).mkdirs();

        // Puzzle class
        File javaFile = new File(srcMainJava + "/" + puzzleName + ".java");
        if (javaFile.exists()) {
            System.out.println("Skipped puzzle class (already exists): " + javaFile.getPath());
        } else {
            try (FileWriter fw = new FileWriter(javaFile)) {
                fw.write(getPuzzleSource(pkg, puzzleName));
            }
            System.out.println("Generated puzzle class: " + javaFile.getPath());
        }

        // Test class
        File testFile = new File(srcTestJava + "/" + puzzleName + "Test.java");
        if (testFile.exists()) {
            System.out.println("Skipped test class (already exists): " + testFile.getPath());
        } else {
            try (FileWriter fw = new FileWriter(testFile)) {
                fw.write(getTestSource(pkg, puzzleName));
            }
            System.out.println("Generated test class: " + testFile.getPath());
        }

        // Input files
        File inputFile = new File(resFolder + "/input");
        if (inputFile.exists()) {
            System.out.println("Input file already exists: " + inputFile.getPath());
        } else {
            inputFile.createNewFile();
            System.out.println("Generated input file: " + inputFile.getPath());
        }

        File inputTestFile = new File(resFolder + "/inputTest");
        if (inputTestFile.exists()) {
            System.out.println("InputTest file already exists: " + inputTestFile.getPath());
        } else {
            inputTestFile.createNewFile();
            System.out.println("Generated inputTest file: " + inputTestFile.getPath());
        }
    }

    private static String getPuzzleSource(String pkg, String className) {
        return "package " + pkg + ";\n\n" + "import java.io.IOException;\n" + "import java.util.List;\n\n"
                + "import helper.Puzzle;\n\n" + "public class " + className + " extends Puzzle {\n\n" + "    public "
                + className + "(String input) throws IOException {\n" + "        super(input);\n"
                + "        parse(getLines());\n" + "    }\n\n" + "    private void parse(List<String> lines) {\n"
                + "        // TODO parsing logic\n" + "    }\n\n" + "    @Override\n"
                + "    public Object getAnswer1() {\n" + "        // TODO\n" + "        return null;\n" + "    }\n\n"
                + "    @Override\n" + "    public Object getAnswer2() {\n" + "        // TODO\n"
                + "        return null;\n" + "    }\n\n"
                + "    public static void main(String[] args) throws IOException {\n" + "        " + className
                + " puzzle = new " + className + "(\"" + pkg.replace('.', '/') + "/input\");\n"
                + "        System.out.println(\"Answer 1: \" + puzzle.getAnswer1());\n"
                + "        System.out.println(\"Answer 2: \" + puzzle.getAnswer2());\n" + "    }\n" + "}\n";
    }

    private static String getTestSource(String pkg, String className) {
        return "package " + pkg + ";\n\n" + "import java.io.IOException;\n" + "import testhelper.PuzzleUnitTest;\n\n"
                + "public class " + className + "Test extends PuzzleUnitTest {\n" + "    public " + className
                + "Test() throws IOException {\n" + "        super(new " + className + "(\"" + pkg.replace('.', '/')
                + "/inputTest\"), null, null);\n" + "    }\n" + "}\n";
    }

    public static void main(String[] args) throws IOException {
        // Example usage:
        createNewPuzzle(2025, 6, "Okidoki");
    }


}
