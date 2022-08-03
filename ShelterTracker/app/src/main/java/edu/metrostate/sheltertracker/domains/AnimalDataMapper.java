package edu.metrostate.sheltertracker.domains;

import java.util.Date;
import java.util.List;

public class AnimalDataMapper implements IAnimalDataMapper{
    /**
     * get information from Animal Database and Animal_Shelter database
     * return as an Animal object
     *
     * @return Animal object
     */

    @Override
    public Animal get(String animalId) {
        MyCSV myCSV = new MyCSV();
        List<String[]> animalData = myCSV.readCSV("animal.csv");
        for (int i = 1; i < animalData.size(); i++) {
            if (animalData.get(i)[1].equals(animalId)) {
                List<String[]> animalShelter = myCSV.readCSV("animal_shelter.csv");
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
                Animal animal = new Animal(data[0], data[1], weight, shelterId, data[3], receiptDate);
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
        List<String[]> animalData = myCSV.readCSV("animal.csv");
        String[] newAnimal = new String[5];
        newAnimal[0] = animal.getName();
        newAnimal[1] = animal.getAnimalId();
        newAnimal[2] = Float.toString(animal.getWeight());
        newAnimal[3] = animal.getAnimalType();
        long time = animal.getReceiptDate().getTime();
        newAnimal[4] = Long.toString(time);
        animalData.add(newAnimal);
        myCSV.writeCSV("animal.csv", animalData);

        List<String[]> animalShelter = myCSV.readCSV("animal_shelter.csv");
        String[] newAnimalShelter = new String[2];
        newAnimalShelter[0] = animal.getAnimalId();
        newAnimalShelter[1] = animal.getShelterId();
        animalShelter.add(newAnimalShelter);
        myCSV.writeCSV("animal_shelter.csv", animalShelter);
    }

    @Override
    public void delete(String animalId) {
        //Isn't required for this project
    }

}
