package main.project1.company;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class PetCompany {
    private Map<String, Shelter> shelterList;

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

    public int enableReceivingAnimal (String shelterId) {
        Shelter shelter = shelterList.get(shelterId);
        if (shelter == null) return -1;
        shelter.setInTaking (true);
        return 0;
    }

    public int disableReceivingAnimal (String shelterId) {
        Shelter shelter = shelterList.get(shelterId);
        if (shelter == null) return -1;
        shelter.setInTaking (false);
        return 0;
    }

    public JSONObject exportAnimalListJSON (String shelterId) {
        Shelter shelter = shelterList.get(shelterId);
        if (shelter == null) return null;
        return shelter.exportAnimalListJSON ();
    }

    public Map <String, Animal> showAnimalList (String shelterId) {
        Shelter shelter = shelterList.get(shelterId);
        if (shelter == null) return null;
        return shelter.getAnimalList ();
    }
}
