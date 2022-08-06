package edu.metrostate.sheltertracker.domains;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Return path to Database location (foundation folder) and resources folder
 */
public class MyPath {
    /**
     * path to database folder (foundation)
     */
    public static String getDatabasePath (Context context, String fileName) {
        File externalDir = context.getExternalFilesDir(null);

        File outputFile = new File(externalDir, fileName);
        String filePath = outputFile.toString();
        return filePath;
    }

    /**
     * path to resources folder
     */
    public static String getResourcePath (Context context, String fileName) {
        File externalDir = context.getExternalFilesDir(null);

        File outputFile = new File(externalDir, fileName);
        String filePath = outputFile.toString();
        return filePath;
    }
}