package edu.metrostate.sheltertracker.domains;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public interface IDataIO {
    Map<String, Shelter> convert (String fileName) throws IOException, ParseException, FileNotFoundException;
    void dataExport (Map<String, Shelter> shelterList) throws FileNotFoundException;
}
