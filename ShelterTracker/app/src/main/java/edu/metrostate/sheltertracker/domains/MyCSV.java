package edu.metrostate.sheltertracker.domains;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.*;

/**
 * Support class to read and write to a CSV file
 */
public class MyCSV {
    /**
     * read CSV filename, return a list of String[]
     */
    public List<String[]> readCSV (Context context, String fileName) {
        String filePath = MyPath.getDatabasePath(context, fileName);
        Log.i("My path", filePath);
        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        List<String[]> allData = new ArrayList<>();
        try {
            FileReader filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(filereader).build();
            allData = csvReader.readAll();
        } catch (Exception e) {
            System.out.println("Can't connect to database " + fileName);
            e.printStackTrace();
        }
        return allData;
    }

    /**
     * write data to CSV fileName
     */
    public void writeCSV (Context context, String fileName, List <String[]> data) {
        String filePath = MyPath.getDatabasePath(context, fileName);
        try {
            FileWriter outputFile = new FileWriter(new File(filePath));
            CSVWriter writer = new CSVWriter(outputFile);

            writer.writeAll(data);

            writer.close();
        }
        catch (IOException e) {
            System.out.println("Can't connect to database " + fileName);
            e.printStackTrace();
        }
    }
}
