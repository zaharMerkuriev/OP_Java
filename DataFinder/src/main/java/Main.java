import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    private static final String TEXT_KEY = "text";
    private static final String FOLDER_KEY = "folder";
    private static final String FILE_KEY = "file";
    private static final String HELP_KEY = "help";
    private static final String HELP = "Possible input: \n" +
            "--name <file> <folder> \n" +
            "--data '<text>' <folder|file>\n" +
            "-h";

    public static void main(String[] args) throws IOException {
        ArgumentParser parser = ArgumentParsers.newFor("data finder").addHelp(false).build();
        parser.addArgument("--data").dest(TEXT_KEY);
        parser.addArgument("--name").dest(FILE_KEY);
        parser.addArgument("-h").dest(HELP_KEY);
        parser.addArgument("folder").dest(FOLDER_KEY);

        try {
            var res = parser.parseArgs(args);
            if (res.get(HELP) != null) {
                System.err.println(HELP);
                return;
            }
            var folder = res.get(FOLDER_KEY);
            var file = res.get(FILE_KEY);
            var fileWorker = new FileWorker(file == null ? null : file.toString(),
                    Paths.get(folder.toString()));
            var listOfPaths = fileWorker.getFiles();
            if (res.get(TEXT_KEY) != null) {
                var stringSearcher = new StringSearcher(listOfPaths, res.get(TEXT_KEY));
                var searchedList = stringSearcher.find();
                if (searchedList.size() > 0) {
                    System.out.println("String find in:");
                    for (var path : searchedList) {
                        System.out.println(path.toString());
                    }
                } else {
                    System.out.println("Files don't contain such string");
                }
            } else {
                for (var path : listOfPaths) {
                    System.out.println(path.toString());
                }
            }
        } catch (ArgumentParserException e) {
            System.err.println(HELP);
        }
    }
}