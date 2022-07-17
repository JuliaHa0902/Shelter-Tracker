package project2.domain;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.opencsv.*;

public class MyCSV {
    public List<String[]> readCSV (String fileName) {
        String filePath = MyPath.getDatabasePath(fileName);
        List<String[]> allData = null;
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

    public void writeCSV (String fileName, List <String[]> data) {
        String filePath = MyPath.getDatabasePath(fileName);
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
