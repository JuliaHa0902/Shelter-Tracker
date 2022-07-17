package project2.domain;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
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

    public static final int FAILED = -1;
    public static final int SUCCESS = 0;

    public PetCompany() {
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
    public String addAnimal (String fileName, String type) throws IOException, ParseException, ClassCastException, FileNotFoundException {
        IDataIO fileData = DataIOFactory.get (type);
        Map <String, Shelter> shelterList = fileData.convert (fileName);
        IAnimalDataMapper animalDataMapper = new AnimalDataMapper();
        IShelterDataMapper shelterDataMapper = new ShelterDataMapper();
        String errorNote = "";
        if (shelterList == null) {
            errorNote += "Nothing to import!\n";
            return errorNote;
        }
        for (String shelterId : shelterList.keySet()) {
            Shelter shelter = shelterDataMapper.get(shelterId);
            if (shelter == null) {
                shelterDataMapper.insert(shelterList.get(shelterId));
            } else {
                if (!shelterList.get(shelterId).getName().equals(shelter.getName())) {
                    String error = String.format("Shelter %s already existed with a different name\n", shelter.getId());
                    errorNote += error;
                }
                if (shelter.getInTaking() == false) {
                    String error = String.format("Shelter %s has stopped receiving animal. Add animals skipped!\n", shelter.getId());
                    errorNote += error;
                    continue;
                }
            }
            Map <String, Animal> animalList = shelterList.get(shelterId).getAnimalList();
            for (String key : animalList.keySet())  {
                Animal animal = animalDataMapper.get(animalList.get(key).getAnimalId());
                if (animal == null) {
                    animalDataMapper.insert(animalList.get(key));
                } else {
                    errorNote += String.format("Animal %s already exist in shelter %s\n", animal.getAnimalId(), animal.getShelterId());
                }
            }
        }
        return errorNote;
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
        IShelterDataMapper shelterDataMapper = new ShelterDataMapper();
        Shelter shelter = shelterDataMapper.get (shelterId);
        if (shelter == null) return FAILED;
        shelter.setInTaking (true);
        shelterDataMapper.update (shelter);
        return SUCCESS;
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
        IShelterDataMapper shelterDataMapper = new ShelterDataMapper();
        Shelter shelter = shelterDataMapper.get (shelterId);
        if (shelter == null) return FAILED;
        shelter.setInTaking (false);
        shelterDataMapper.update (shelter);
        return SUCCESS;
    }

    /**
     * Export all animals in shelter with ID shelterId to a JSONObject
     *
     * @param type type of file that should be export
     * @return JSONObject of the list of animals in the shelter
     * @return null if the shelter ID doesn't exist
     *
     */
    public int exportAnimalList (String type) {
        IShelterDataMapper shelterDataMapper = new ShelterDataMapper();
        Map<String, Shelter> shelterList = shelterDataMapper.getShelterList ();
        IDataIO dataIO = DataIOFactory.get(type);
        try {
            dataIO.dataExport (shelterList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return FAILED;
        }
        return SUCCESS;
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
        IShelterDataMapper shelterDataMapper = new ShelterDataMapper();
        Shelter shelter = shelterDataMapper.get (shelterId);
        if (shelter == null) return null;
        return shelter.getAnimalList ();
    }

    public Animal getAnimalInfo (String animalId) {
        IAnimalDataMapper animalDataMapper = new AnimalDataMapper();
        Animal animal = animalDataMapper.get(animalId);

        return animal;
    }

    public Shelter getShelterInfo (String shelterId) {
        IShelterDataMapper shelterDataMapper = new ShelterDataMapper();
        Shelter shelter = shelterDataMapper.get (shelterId);
        return shelter;
    }

    public String[] getShelterList () {
        IShelterDataMapper shelterDataMapper = new ShelterDataMapper();
        Map<String, Shelter> shelterMap = shelterDataMapper.getShelterList();
        String[] shelterList = new String[shelterMap.size()];
        int i = 0;
        for (String key: shelterMap.keySet()) {
            shelterList[i] = key + " - " + shelterMap.get(key).getName();
            i++;
        }
        return shelterList;
    }
}
