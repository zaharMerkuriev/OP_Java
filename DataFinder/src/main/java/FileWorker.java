import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

class FileWorker {
    private String fileName;
    private Path root;
    private ArrayList<Path> filesSearched;

    FileWorker(String fileName, Path root) {
        this.fileName = fileName;
        this.root = root;
        filesSearched = new ArrayList<>();
    }

    ArrayList<Path> getFiles() {
        try {
            Files.walkFileTree(root, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) {
                    if (fileName == null) {
                        filesSearched.add(path);
                    } else if (getFileNameOnly(path).equals(fileName)) {
                        filesSearched.add(path);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filesSearched;
    }

    private String getFileNameOnly(Path path) {
        var index = path.toFile().getName().lastIndexOf(".");
        return index > 0 ? fileName.substring(0, index) : fileName;
    }
}