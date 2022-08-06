package edu.metrostate.sheltertracker.domains;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class ShelterDataMapper maps the information of the object to Shelter database
 * @author Dung Thi Thuy Ha
 */
public class ShelterDataMapper implements IShelterDataMapper{
    Context context;

    public ShelterDataMapper (Context context) {
        this.context = context;
        MyCSV myCSV = new MyCSV();
        List<String[]> shelterData = myCSV.readCSV(this.context, "shelter.csv");
        if (shelterData == null)
            shelterData = createFile();
        List<String[]> animalShelterData = myCSV.readCSV(this.context, "animal.csv");
        if (animalShelterData == null) {
            animalShelterData = createAnimalShelterFile();
        }
    }

    private List<String[]> createFile () {
        MyCSV myCSV = new MyCSV();
        List<String[]> shelterData = new ArrayList<>();
        String[] data = {"shelter_id", "shelter_name", "in_taking"};
        shelterData.add(data);
        myCSV.writeCSV(this.context, "shelter.csv", shelterData);
        return shelterData;
    }

    private List<String[]> createAnimalShelterFile() {
        MyCSV myCSV = new MyCSV();
        List<String[]> animalShelterData = new ArrayList<>();
        String[] data = {"animal_id", "shelter_id"};
        animalShelterData.add(data);
        myCSV.writeCSV(this.context, "animal_shelter.csv", animalShelterData);
        return animalShelterData;
    }
    /**
     * get information from Shelter Database and Animal_Shelter database
     * return as a Shelter object
     * @return Shelter object
     */
    @Override
    public Shelter get(String shelterId) {
        MyCSV myCSV = new MyCSV();
        Shelter shelter = new Shelter(shelterId);
        boolean found = false;
        List<String[]> shelterData = myCSV.readCSV(this.context, "shelter.csv");

        for (int i = 1; i < shelterData.size(); i++) {
            if (shelterData.get(i)[0].equals(shelterId)) {
                found = true;
                shelter.setShelterName(shelterData.get(i)[1]);
                shelter.setInTaking(Boolean.parseBoolean(shelterData.get(i)[2]));
                break;
            }
        }
        if (!found) return null;
        Map<String, Animal> animalList = new HashMap<>();
        animalList = getAnimalList(shelterId);
        for (String key : animalList.keySet()) {
            shelter.addAnimal(animalList.get(key));
        }
        return shelter;
    }

    /**
     * update shelter information in Shelter Database
     */
    @Override
    public void update(Shelter shelter) {
        MyCSV myCSV = new MyCSV();
        List<String[]> shelterData = myCSV.readCSV(this.context, "shelter.csv");

        for (int i = 1; i < shelterData.size(); i++) {
            if (shelterData.get(i)[0].equals(shelter.getShelterId())) {
                shelterData.get(i)[1] = shelter.getShelterName();
                shelterData.get(i)[2] = Boolean.toString(shelter.getInTaking());
                break;
            }
        }
        myCSV.writeCSV(this.context, "shelter.csv", shelterData);
    }

    /**
     * insert a new shelter to Shelter Database
     */
    @Override
    public void insert(Shelter shelter) {
        MyCSV myCSV = new MyCSV();
        List<String[]> shelterData = myCSV.readCSV(this.context, "shelter.csv");

        String[] newShelter = new String[3];
        newShelter[0] = shelter.getShelterId();
        newShelter[1] = shelter.getShelterName();
        newShelter[2] = Boolean.toString(shelter.getInTaking());
        shelterData.add(newShelter);
        myCSV.writeCSV(this.context, "shelter.csv", shelterData);
    }

    @Override
    public void delete(String shelterId) {
        //Isn't required for this project
    }

    /**
     * return all shelters inside this database
     */
    public Map<String, Shelter> getShelterList () {
        MyCSV myCSV = new MyCSV();
        List<String[]> shelterData = myCSV.readCSV(this.context, "shelter.csv");

        List<String[]> animalShelterData = myCSV.readCSV(this.context, "animal_shelter.csv");

        AnimalDataMapper animalDataMapper = new AnimalDataMapper(this.context);
        Map <String, Shelter> shelterList = new HashMap<>();
        for (int i = 1; i < shelterData.size(); i++) {
            Shelter shelter = new Shelter(shelterData.get(i)[0]);
            shelter.setShelterName(shelterData.get(i)[1]);
            shelter.setInTaking(Boolean.parseBoolean(shelterData.get(i)[2]));
            for (int j = 1; j < animalShelterData.size(); j++) {
                String shelterId = animalShelterData.get(j)[1];
                if (shelterId.equals(shelter.getShelterId())) {
                    Animal animal = animalDataMapper.get(animalShelterData.get(j)[0]);
                    shelter.addAnimal(animal);
                }
            }
            shelterList.put(shelter.getShelterId(), shelter);
        }
       return shelterList;
    }

    /**
     * return a map contains all animals in shelterId
     */
    public Map<String, Animal> getAnimalList (String shelterId) {
        MyCSV myCSV = new MyCSV();
        AnimalDataMapper animalDataMapper = new AnimalDataMapper(this.context);
        Map<String, Animal> animalList = new HashMap<>();
        List<String[]> animalShelterData = myCSV.readCSV(this.context, "animal_shelter.csv");

        for (int i = 1; i < animalShelterData.size(); i++) {
            String animalId = animalShelterData.get(i)[0];
            if (animalShelterData.get(i)[1].equals(shelterId)) {
                Animal animal = animalDataMapper.get(animalId);
                animalList.put(animalId, animal);
            }
        }
        return animalList;
    }

}
