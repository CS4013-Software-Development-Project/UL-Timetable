package persistence;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A lightweight persistence helper that reads and writes CSV files.
 * Features basic caching ability, will full control over cache flush,
 * reload, and save to disk.
 * <br>
 * As a safety, ensures that the filestructure it is provided exists
 * before writing/reading from it. By default, will create missing files
 * as well as any missing directories.
 *
 * @author Kuba Rodak (24436755)
 */
public class Repository {
    /** The absolute path of the backing CSV file. */
    private final Path path;
    /** Cached contents of the file. */
    private List<String> fileContents;

    /**
     * Creates a new repository for the given file path.
     *
     * <p>
     * The constructor guarantees that a file with a {@code .csv}
     * extension exists.  If the file does not exist it will be
     * created. Any missing parent directories will be created also.
     * </p>
     *
     * @param filePath the path to the file
     * @throws RuntimeException if the repository cannot be created
     */
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

    /**
     * Returns all lines currently stored in the file.
     *
     * <p>
     * If the file has not been read yet, then this function will read it and
     * return the contents. If the file has already been cached, the cached copy
     * will be returned instead.
     * <br>
     * This is intended for minimizing I/O ops on the host disk.
     * </p>
     *
     * @return a list of all lines in the repository
     * @throws RuntimeException if the file cannot be read
     */
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

    /**
     * Flush the cache.
     */
    public void clear() {
        this.fileContents = null;
    }

    /**
     * Appends a line to the in‑memory cache.
     */
    public void addLine(String line) {
        if (this.fileContents == null) {
            this.fileContents = new ArrayList<>();
        }
        this.fileContents.add(line);
    }

    /**
     * Save the in-memory contents to disk.
     */
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

    /**
     * Retrieves a specific line from the repository.
     */
    public String getLine(int index) {
        if (this.fileContents == null) {
            this.readAll();
        }

        return this.fileContents.get(index);
    }
}
