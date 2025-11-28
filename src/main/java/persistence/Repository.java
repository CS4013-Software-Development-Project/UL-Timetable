package persistence;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final Path path;
    private List<String> fileContents;

    public Repository(String filePath) {

        if (!filePath.endsWith(".csv")) {
            filePath += ".csv";
        }

        this.path = Paths.get(filePath).toAbsolutePath();

        try {
            //first make sure directories exist
            Files.createDirectories(path.getParent());
            //then create the file if it is missing
            Files.createFile(path);
        }
        catch (FileAlreadyExistsException ignored) {}  //this is OK because we just want it there
        catch (IOException e) {
            throw new RuntimeException("Error opening repository", e);
        }
    }

    public List<String> readAll() {

        if (this.fileContents != null) {
            return this.fileContents;
        }

        try {
            this.fileContents = Files.readAllLines(path);
            return this.fileContents;
        }
        catch(Exception e) {
            throw new RuntimeException("Error reading from repository", e);
        }
    }

    public void clear() {
        this.fileContents = null;
    }

    public void addLine(String line) {
        if (this.fileContents == null) {
            this.fileContents = new ArrayList<>();
        }
        this.fileContents.add(line);
    }

    public void save() {
        try {
            if (fileContents == null)
                return;
            Files.write(path, fileContents);
        }
        catch (Exception e) {
            throw new RuntimeException("Error saving repository file", e);
        }
    }

    public String getLine(int index) {
        if (this.fileContents == null) {
            this.readAll();
        }

        return this.fileContents.get(index);
    }
}
