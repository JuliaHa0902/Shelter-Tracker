package edu.metrostate.sheltertracker.domains;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShelterTrackerApplication extends Application {

    private  Map <String, Animal> animalMap = new HashMap<>();
    private  Map<String, Shelter> shelterMap = new HashMap<>();
    public static final int FAILED = -1;
    public static final int SUCCESS = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        this.shelterMap = getShelterMap();
        this.animalMap = getAnimalMap();
    }


    public Map <String, Shelter> getShelterMap() {
        IShelterDataMapper shelterDataMapper = new ShelterDataMapper(this.getApplicationContext());
        Map<String, Shelter> shelterMap = shelterDataMapper.getShelterList();
        return shelterMap;
    }

    public List<Shelter> getShelterList() {
        List <Shelter> shelterList = new ArrayList<>(shelterMap.values());
        return shelterList;
    }

    public Map <String, Animal> getAnimalMap() {
        IAnimalDataMapper animalDataMapper = new AnimalDataMapper(this.getApplicationContext());
        Map<String, Animal> animalMap = animalDataMapper.getAnimalList();
        return animalMap;
    }

    public List<Animal> getAnimalList() {
        List<Animal> animalList = new ArrayList<>(animalMap.values());
        return  animalList;
    }

    public String addAnimal (String fileName, String type) throws IOException, ParseException, ClassCastException, FileNotFoundException {
        IDataIO fileData = DataIOFactory.get (this.getApplicationContext(), type);
        Map <String, Shelter> importShelters = fileData.convert (fileName);
        IAnimalDataMapper animalDataMapper = new AnimalDataMapper(this.getApplicationContext());
        IShelterDataMapper shelterDataMapper = new ShelterDataMapper(this.getApplicationContext());
        String errorNote = "";
        if (importShelters == null) {
            errorNote += "Nothing to import!\n";
            return errorNote;
        }
        for (String shelterId : importShelters.keySet()) {
            Shelter shelter = this.shelterMap.get(shelterId);
            if (shelter == null) {
                shelterDataMapper.insert(importShelters.get(shelterId));
                this.shelterMap.put(shelterId, importShelters.get(shelterId));
            } else {
                if (!importShelters.get(shelterId).getShelterName().equals(shelter.getShelterName())) {
                    String error = String.format("Shelter %s already existed with a different name. Discard new name!\n", shelter.getShelterId());
                    errorNote += error;
                }
                if (shelter.getInTaking() == false) {
                    String error = String.format("Shelter %s has stopped receiving animal. Add animals skipped!\n", shelter.getShelterId());
                    errorNote += error;
                    continue;
                }
            }
            Map <String, Animal> importAnimals = importShelters.get(shelterId).getAnimalList();
            for (String key : importAnimals.keySet())  {
                Animal animal = animalMap.get(key);
                if (animal == null) {
                    animalDataMapper.insert(importAnimals.get(key));
                    animalMap.put(key, importAnimals.get(key));
                    shelterMap.get(shelterId).addAnimal(importAnimals.get(key));
                } else {
                    errorNote += String.format("Animal %s already exist in shelter %s\n", animal.getAnimalId(), animal.getShelterId());
                }
            }
        }
        return errorNote;
    }

    public int enableReceivingAnimal (String shelterId) {
        Shelter shelter = shelterMap.get (shelterId);
        if (shelter == null) return FAILED;
        shelter.setInTaking (true);
        IShelterDataMapper shelterDataMapper = new ShelterDataMapper(this.getApplicationContext());
        shelterDataMapper.update (shelter);
        return SUCCESS;
    }

    public int disableReceivingAnimal (String shelterId) {
        Shelter shelter = shelterMap.get (shelterId);
        if (shelter == null) return FAILED;
        shelter.setInTaking (false);
        IShelterDataMapper shelterDataMapper = new ShelterDataMapper(this.getApplicationContext());
        shelterDataMapper.update (shelter);
        return SUCCESS;
    }
    public int exportAnimalList (String type) {
        IDataIO dataIO = DataIOFactory.get(this.getApplicationContext(), type);
        try {
            dataIO.dataExport (shelterMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return FAILED;
        }
        return SUCCESS;
    }

    public List <Animal> showAnimalsByShelter (String shelterId) {
        Shelter shelter = shelterMap.get (shelterId);
        if (shelter == null) return null;
        List<Animal> animalList = new ArrayList<>(shelter.getAnimalList().values());
        return animalList;
    }

    public Animal getAnimalInfo (String animalId) {
        Animal animal = animalMap.get(animalId);

        return animal;
    }

    public Shelter getShelterInfo (String shelterId) {
        Shelter shelter = shelterMap.get (shelterId);
        return shelter;
    }

    public int removeAnimal (String animalId) {
        IAnimalDataMapper animalDataMapper = new AnimalDataMapper(this.getApplicationContext());
        Date today = new Date();
        Animal animal = animalMap.get(animalId);
        if (!animal.getReleaseDate().equals(new Date(0)))
            return FAILED;
        animal.setReleaseDate(today);
        animalDataMapper.delete(animal);

        String shelterId = animal.getShelterId();
        shelterMap.get(shelterId).getAnimalList().remove (animalId);

        return SUCCESS;
    }

}
