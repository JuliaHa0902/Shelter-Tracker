package project2.domain;

import java.nio.file.Path;

/**
 * Return path to Database location (foundation folder) and resources folder
 */
public class MyPath {
    /**
     * path to database folder (foundation)
     */
    public static String getDatabasePath (String fileName) {
        Path resourceDirectory = Path.of("src", "main", "java", "project2", "foundation", fileName);
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        return absolutePath;
    }

    /**
     * path to resources folder
     */
    public static String getResourcePath (String fileName) {
        Path resourceDirectory = Path.of("src", "main", "resources", fileName);
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        return absolutePath;
    }
}