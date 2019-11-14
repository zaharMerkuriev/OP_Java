import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Path;
import java.util.ArrayList;

class StringSearcher {
    private final static int BUFFER_SIZE = 4096;
    private ArrayList<Path> pathList;
    private ArrayList<Path> searchedList;
    private String searchingString;
    private CharBuffer buffer;

    StringSearcher(ArrayList<Path> pathList, String searchingString) {
        this.pathList = pathList;
        this.searchingString = searchingString;
        buffer = CharBuffer.allocate(BUFFER_SIZE);
        searchedList = new ArrayList<>();
    }

    ArrayList<Path> find() throws IOException {
        for (Path path : pathList) {
            var reader = new BufferedReader(new FileReader(path.toFile()));
            var readIndex = reader.read(buffer);
            var boyerMoore = new BoyerMoore(searchingString);
            while (readIndex != -1) {
                buffer.flip();
                var chars = new char[buffer.remaining()];
                buffer.get(chars);
                if (boyerMoore.find(chars)) {
                    searchedList.add(path);
                    break;
                }
                buffer.clear();
                readIndex = reader.read(buffer);
            }
            buffer.clear();
        }
        return searchedList;
    }
}