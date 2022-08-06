package edu.metrostate.sheltertracker.domains;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalDataMapper implements IAnimalDataMapper{
    Context context;

    public AnimalDataMapper (Context context) {
        this.context = context;
        MyCSV myCSV = new MyCSV();
        List<String[]> animalData = myCSV.readCSV(this.context, "animal.csv");
        if (animalData == null)
            animalData = createFile();
        List<String[]> animalShelterData = myCSV.readCSV(this.context, "animal.csv");
        if (animalShelterData == null) {
            animalShelterData = createAnimalShelterFile();
        }
    }

    private List<String[]> createFile () {
        MyCSV myCSV = new MyCSV();
        List<String[]> animalData = new ArrayList<>();
        String[] data = {"animal_name", "animal_id", "weight", "animal_type", "receipt_date", "release_date"};

        animalData.add(data);
        myCSV.writeCSV(this.context, "animal.csv", animalData);
        return animalData;
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
     * get information from Animal Database and Animal_Shelter database
     * return as an Animal object
     *
     * @return Animal object
     */

    @Override
    public Animal get(String animalId) {
        MyCSV myCSV = new MyCSV();
        List<String[]> animalData = myCSV.readCSV(this.context, "animal.csv");

        for (int i = 1; i < animalData.size(); i++) {
            if (animalData.get(i)[1].equals(animalId)) {
                List<String[]> animalShelter = myCSV.readCSV(this.context, "animal_shelter.csv");
                String shelterId = "";
                for (int j = 1; j < animalShelter.size(); j++) {
                    if (animalShelter.get(j)[0].equals(animalId)) {
                        shelterId = animalShelter.get(j)[1];
                        break;
                    }
                }
                String[] data = animalData.get(i);
                float weight = Float.parseFloat(data[2]);
                Date receiptDate = new Date( Long.parseLong (data[4]));
                Date releaseDate = new Date( Long.parseLong (data[5]));
                Animal animal = new Animal(data[0], data[1], weight, shelterId, data[3], receiptDate, releaseDate);
                return animal;
            }
        }
        return null;
    }

    /**
     * update animal information in Animal Database and Animal_Shelter database
     */
    @Override
    public void update(Animal animal) {
        //Isn't required for this project
    }

    /**
     * insert a new animal to Animal Database and Animal_Shelter database
     */
    @Override
    public void insert(Animal animal) {
        MyCSV myCSV = new MyCSV();
        List<String[]> animalData = myCSV.readCSV(this.context, "animal.csv");
        String[] newAnimal = new String[6];
        newAnimal[0] = animal.getName();
        newAnimal[1] = animal.getAnimalId();
        newAnimal[2] = Float.toString(animal.getWeight());
        newAnimal[3] = animal.getAnimalType();
        long time = animal.getReceiptDate().getTime();
        newAnimal[4] = Long.toString(time);
        newAnimal[5] = Long.toString(0);
        animalData.add(newAnimal);
        myCSV.writeCSV(this.context, "animal.csv", animalData);

        List<String[]> animalShelter = myCSV.readCSV(this.context, "animal_shelter.csv");
        String[] newAnimalShelter = new String[2];
        newAnimalShelter[0] = animal.getAnimalId();
        newAnimalShelter[1] = animal.getShelterId();
        animalShelter.add(newAnimalShelter);
        myCSV.writeCSV(this.context, "animal_shelter.csv", animalShelter);
    }

    @Override
    public void delete(Animal animal) {
        String animalId = animal.getAnimalId();
        MyCSV myCSV = new MyCSV();
        List<String[]> animalData = myCSV.readCSV(this.context, "animal.csv");

        for (int i = 1; i < animalData.size(); i++) {
            if (animalData.get(i)[1].equals(animalId)) {
                List<String[]> animalShelter = myCSV.readCSV(this.context, "animal_shelter.csv");
                for (int j = 1; j < animalShelter.size(); j++) {
                    if (animalShelter.get(j)[0].equals(animalId)) {
                        animalShelter.remove(j);
                        break;
                    }
                }
                myCSV.writeCSV(this.context, "animal_shelter.csv", animalShelter);

                long time = animal.getReleaseDate().getTime();
                Log.i ("Time Release", Long.toString(time));
                animalData.get(i)[5] = Long.toString(time);
                Log.i ("animal ", animalData.get(i).toString());
                myCSV.writeCSV(this.context, "animal.csv", animalData);
            }
        }
    }

    public Map<String, Animal> getAnimalList () {
        MyCSV myCSV = new MyCSV();
        List<String[]> animalData = myCSV.readCSV(this.context, "animal.csv");

        Map<String, Animal> animalMap = new HashMap<>();
        for (int i = 1; i < animalData.size(); i++) {
            List<String[]> animalShelter = myCSV.readCSV(this.context, "animal_shelter.csv");
            String shelterId = "";
            for (int j = 1; j < animalShelter.size(); j++) {
                if (animalShelter.get(j)[0].equals(animalData.get(i)[1])) {
                    shelterId = animalShelter.get(j)[1];
                    break;
                }
            }
            String[] data = animalData.get(i);
            float weight = Float.parseFloat(data[2]);
            Date receiptDate = new Date( Long.parseLong (data[4]));
            Date releaseDate = new Date( Long.parseLong (data[5]));
            Animal animal = new Animal(data[0], data[1], weight, shelterId, data[3], receiptDate, releaseDate);
            animalMap.put(animalData.get(i)[1], animal);
        }
        return animalMap;
    }

}
