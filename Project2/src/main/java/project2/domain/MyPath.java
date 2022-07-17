package project2.domain;

import java.nio.file.Path;

public class MyPath {
    public static String getDatabasePath (String fileName) {
        Path resourceDirectory = Path.of("src", "main", "java", "project2", "foundation", fileName);
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        return absolutePath;
    }

    public static String getResourcePath (String fileName) {
        Path resourceDirectory = Path.of("src", "main", "resources", fileName);
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        return absolutePath;
    }
}