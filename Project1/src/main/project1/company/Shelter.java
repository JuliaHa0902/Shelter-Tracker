package main.project1.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Shelter {
    private String shelterId;
    private Boolean inTaking;
    private Map<String, Animal> animalList;
    public Shelter (String shelterId) {
        this.shelterId = shelterId;
        this.inTaking = true;
        animalList = new HashMap<> ();
    }

    public void addAnimal (Animal animal) {
        if (inTaking) {
            animalList.put(animal.getId(), animal);
            System.out.printf("Successfully added animal id %s to shelter id %s \n", animal.getId(), shelterId);
        } else {
            System.out.printf("Cannot add animal id %s: shelter id %s has stopped receiving animal \n", animal.getId(), shelterId);
        }
    }

    public JSONObject exportAnimalListJSON () {
        JSONArray animalJSONArray = new JSONArray();
        for (String animalId : animalList.keySet()) {
            Animal animal = animalList.get(animalId);
            Map animalJSON = new LinkedHashMap(6);
            animalJSON.put("shelter_id", animal.getShelterId());
            animalJSON.put("animal_type", animal.getAnimalType());
            animalJSON.put("animal_name", animal.getName());
            animalJSON.put("animal_id", animal.getId());
            animalJSON.put("weight", animal.getWeight());
            animalJSON.put("receipt_date", animal.getReceiptDate().getTime());
            animalJSONArray.add(animalJSON);
        }
        JSONObject animalListJSON = new JSONObject();
        animalListJSON.put("shelter_" + shelterId, animalJSONArray);
        return animalListJSON;
    }

    public Map <String, Animal> getAnimalList () {
        return animalList;
    }

    public void setInTaking (Boolean value) {
        inTaking = value;
    }

    public Boolean getInTaking () {
        return inTaking;
    }

    public void setId (String shelterId) {
        this.shelterId = shelterId;
    }

    public String getId () {
        return shelterId;
    }
}
