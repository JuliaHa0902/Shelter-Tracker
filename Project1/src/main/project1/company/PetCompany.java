package main.project1.company;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 * Class PetCompany contains information about all the shelters that company has using a HashMap of Shelter
 * Each shelter has a unique ID
 *
 * @author Dung Thi Thuy Ha
 * Project 1
 * Class ICS 372
 */
public class PetCompany {
    private Map<String, Shelter> shelterList;

    /**
     * Constructor for PetCompany. Will load infor from a JSON file and create a shelterList objects contains
     * information of all shelters and animals in that JSON
     *
     * @param fileName name of the file that will be used as input. This file should be placed under resources folder
     */
    public PetCompany(String fileName) {
        shelterList = new HashMap<>();
        try {
            addAnimal(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    /**
     * Load information of animal from JSON file and add new animals to our current shelter list.
     *
     * @param fileName name of the file that will be used as input. This file should be placed under resources folder
     * @throws IOException if the file is not accessible
     * @throws ParseException if the file doesnt have the same format as the sample file
     * @throws ClassCastException if the file doesnt have the same format as the sample file
     *
     */
    public void addAnimal (String fileName) throws IOException, ParseException, ClassCastException{
        Path resourceDirectory = Path.of("src", "resources", fileName);
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        Object obj = new JSONParser().parse(new FileReader(absolutePath));
        JSONObject jsonObject = (JSONObject) obj;
        for (Object key : jsonObject.keySet()) {
            JSONArray animalList = (JSONArray) jsonObject.get(key);
            for (int i = 0; i < animalList.size(); i++) {
                JSONObject animalJson = (JSONObject) animalList.get(i);
                Animal animal = new Animal(animalJson);
                String shelterId = animal.getShelterId();
                if (shelterList.get(shelterId) == null) {
                    shelterList.put(shelterId, new Shelter(shelterId));
                }
                Shelter shelter = shelterList.get(shelterId);
                shelter.addAnimal (animal);
            }
        }
    }

    /**
     * Enable shelter with ID shelterId to receive animal
     *
     * @param shelterId ID of the shelter that should be enabled for receving animal
     * @return 0 if the shelter ID is valid
     * @return -1 if the shelter ID is invalid
     *
     */
    public int enableReceivingAnimal (String shelterId) {
        Shelter shelter = shelterList.get(shelterId);
        if (shelter == null) return -1;
        shelter.setInTaking (true);
        return 0;
    }

    /**
     * Disable shelter with ID shelterId to receive animal
     *
     * @param shelterId ID of the shelter that should be disabled for receving animal
     * @return 0 if the shelter ID is valid
     * @return -1 if the shelter ID is invalid
     *
     */
    public int disableReceivingAnimal (String shelterId) {
        Shelter shelter = shelterList.get(shelterId);
        if (shelter == null) return -1;
        shelter.setInTaking (false);
        return 0;
    }

    /**
     * Export all animals in shelter with ID shelterId to a JSONObject
     *
     * @param shelterId ID of the shelter that should be export
     * @return JSONObject of the list of animals in the shelter
     * @return null if the shelter ID doesn't exist
     *
     */
    public JSONObject exportAnimalListJSON (String shelterId) {
        Shelter shelter = shelterList.get(shelterId);
        if (shelter == null) return null;
        return shelter.exportAnimalListJSON ();
    }

    /**
     * Return the list of all animals in shelter with ID shelterId
     *
     * @param shelterId ID of the shelter that needs to show animal list
     * @return a Map <String, Animal> contains all the animal of shelterId
     * @return null if the shelter ID doesn't exist
     *
     */
    public Map <String, Animal> showAnimalList (String shelterId) {
        Shelter shelter = shelterList.get(shelterId);
        if (shelter == null) return null;
        return shelter.getAnimalList ();
    }
}
