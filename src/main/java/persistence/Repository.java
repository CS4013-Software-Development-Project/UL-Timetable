package persistence;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.List;

public class Repository {
    private final Path path;
    private String[] fileContents;

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

    public String[] readAll() {

        if (this.fileContents != null) {
            return this.fileContents;
        }

        try {
            List<String> lines = Files.readAllLines(path);
            this.fileContents = lines.toArray(new String[0]);
            return this.fileContents;
        }
        catch(Exception e) {
            throw new RuntimeException("Error reading from repository", e);
        }
    }

    public void clear() {
        this.fileContents = null;
    }
}
