package edu.metrostate.sheltertracker.domains;

import android.content.Context;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonIO implements IDataIO{
    public Context context;

    public JsonIO(Context context) {
        this.context = context;
    }

    /**
     * Read json file in path
     * return an array list of Shelter
     */
    @Override
    public Map<String, Shelter> convert(String fileName) throws IOException, ParseException, FileNotFoundException {
        String filePath = MyPath.getResourcePath(this.context, fileName) + ".json";
        Map<String, Shelter> shelterList = new HashMap<>();
        Object obj = new JSONParser().parse(new FileReader(filePath));
        JSONObject jsonObject = (JSONObject) obj;
        for (Object key : jsonObject.keySet()) {
            JSONArray animalList = (JSONArray) jsonObject.get(key);
            for (int i = 0; i < animalList.size(); i++) {
                JSONObject animalJson = (JSONObject) animalList.get(i);
                Animal animal = new Animal(animalJson);
                String shelterId = animal.getShelterId();
                if (shelterList.get(shelterId) == null) {
                    String shelterName = (String) animalJson.get("shelter_name");
                    shelterList.put(shelterId, new Shelter(shelterId, shelterName));
                }
                // should check for shelter different name here
                Shelter shelter = shelterList.get(shelterId);
                shelter.addAnimal (animal);
            }
        }
        return shelterList;
    }
    /**
     * Export Shelter information to a json file, placed in resources
     */
    @Override
    public void dataExport(Map<String, Shelter> shelterList) throws FileNotFoundException {
        JSONArray animalJSONArray = new JSONArray();
        for (String shelterId : shelterList.keySet()) {
            Map<String, Animal> animalList = shelterList.get(shelterId).getAnimalList();
            for (String animalId : animalList.keySet()) {
                Animal animal = animalList.get(animalId);
                Map animalJSON = new LinkedHashMap(6);
                animalJSON.put("shelter_id", animal.getShelterId());
                animalJSON.put("shelter_name", shelterList.get(shelterId).getShelterName());
                animalJSON.put("animal_type", animal.getAnimalType());
                animalJSON.put("animal_name", animal.getName());
                animalJSON.put("animal_id", animal.getAnimalId());
                animalJSON.put("weight", animal.getWeight());
                animalJSON.put("receipt_date", animal.getReceiptDate().getTime());
                animalJSONArray.add(animalJSON);
            }
        }
        JSONObject animalListJSON = new JSONObject();
        animalListJSON.put("shelters", animalJSONArray);

        String filePath = MyPath.getResourcePath(this.context, "shelterExport.json");
        PrintWriter pw = new PrintWriter(filePath);
        pw.write(animalListJSON.toJSONString());
        pw.flush();
        pw.close();
    }
}
