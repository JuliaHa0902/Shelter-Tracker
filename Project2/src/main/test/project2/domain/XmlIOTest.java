package project2.domain;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class XmlIOTest {

    @Test
    public void convert() {
        XmlIO xmlIO = new XmlIO();
        try {
            Map<String, Shelter> shelterGet = xmlIO.convert("test_xml");
            assertEquals(shelterGet.get("54321").getName(), "Test Shelter");
            assertEquals(shelterGet.get("54321").getAnimalList().size(), 1);
            Map<String, Animal> animalMap = shelterGet.get("54321").getAnimalList();
            assertEquals(animalMap.get("abcd").getName(), "Lucky");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void dataExport() {
        XmlIO xmlIO = new XmlIO();
        Map <String, Shelter> shelterMap = new HashMap<>();
        Shelter shelter = new Shelter("12345", "Test Shelter");
        Float weight = Float.parseFloat("1.2");
        Date date = new Date(12345678);
        Animal animal = new Animal("Test Animal", "54321", weight, "12345", "cat", date);
        shelter.addAnimal(animal);
        shelterMap.put("12345", shelter);
        try {
            xmlIO.dataExport(shelterMap);
            Map<String, Shelter> shelterGet = xmlIO.convert("shelterExport");
            assertEquals(shelterGet.get("12345").getName(), "Test Shelter");
            assertEquals(shelterGet.get("12345").getAnimalList().size(), 1);
            Map<String, Animal> animalMap = shelterGet.get("12345").getAnimalList();
            assertEquals(animalMap.get("54321").getName(), "Test Animal");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}