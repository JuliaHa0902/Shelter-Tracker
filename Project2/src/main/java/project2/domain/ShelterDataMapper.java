package project2.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShelterDataMapper implements IShelterDataMapper{
    /**
     * get information from Shelter Database and Animal_Shelter database
     * return as a Shelter object
     *
     * @return Shelter object
     */
    @Override
    public Shelter get(String shelterId) {
        MyCSV myCSV = new MyCSV();
        Shelter shelter = new Shelter(shelterId);
        boolean found = false;
        List<String[]> shelterData = myCSV.readCSV("shelter.csv");
        for (int i = 1; i < shelterData.size(); i++) {
            if (shelterData.get(i)[0].equals(shelterId)) {
                found = true;
                shelter.setName(shelterData.get(i)[1]);
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
     * update shelter information in Shelter Database and Animal_Shelter database
     */
    @Override
    public void update(Shelter shelter) {
        MyCSV myCSV = new MyCSV();
        List<String[]> shelterData = myCSV.readCSV("shelter.csv");
        for (int i = 1; i < shelterData.size(); i++) {
            if (shelterData.get(i)[0].equals(shelter.getId())) {
                shelterData.get(i)[1] = shelter.getName();
                shelterData.get(i)[2] = Boolean.toString(shelter.getInTaking());
                break;
            }
        }
        myCSV.writeCSV("shelter.csv", shelterData);
    }

    /**
     * insert a new shelter to Shelter Database and Animal_Shelter database
     */
    @Override
    public void insert(Shelter shelter) {
        MyCSV myCSV = new MyCSV();
        List<String[]> shelterData = myCSV.readCSV("shelter.csv");
        String[] newShelter = new String[3];
        newShelter[0] = shelter.getId();
        newShelter[1] = shelter.getName();
        newShelter[2] = Boolean.toString(shelter.getInTaking());
        shelterData.add(newShelter);
        myCSV.writeCSV("shelter.csv", shelterData);
    }

    @Override
    public void delete(String shelterId) {
        //Isn't required for this project
    }

    public Map<String, Shelter> getShelterList () {
        MyCSV myCSV = new MyCSV();
        List<String[]> shelterData = myCSV.readCSV("shelter.csv");
        List<String[]> animalShelterData = myCSV.readCSV("animal_shelter.csv");
        AnimalDataMapper animalDataMapper = new AnimalDataMapper();
        Map <String, Shelter> shelterList = new HashMap<>();
        for (int i = 1; i < shelterData.size(); i++) {
            Shelter shelter = new Shelter(shelterData.get(i)[0]);
            shelter.setName(shelterData.get(i)[1]);
            shelter.setInTaking(Boolean.parseBoolean(shelterData.get(i)[2]));
            for (int j = 1; j < animalShelterData.size(); j++) {
                String shelterId = animalShelterData.get(j)[1];
                if (shelterId.equals(shelter.getId())) {
                    Animal animal = animalDataMapper.get(animalShelterData.get(j)[0]);
                    shelter.addAnimal(animal);
                }
            }
            shelterList.put(shelter.getId(), shelter);
        }
       return shelterList;
    }

    private Map<String, Animal> getAnimalList (String shelterId) {
        MyCSV myCSV = new MyCSV();
        AnimalDataMapper animalDataMapper = new AnimalDataMapper();
        Map<String, Animal> animalList = new HashMap<>();
        List<String[]> animalShelterData = myCSV.readCSV("animal_shelter.csv");
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