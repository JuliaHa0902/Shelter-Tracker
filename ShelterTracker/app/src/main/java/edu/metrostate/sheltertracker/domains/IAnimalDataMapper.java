package edu.metrostate.sheltertracker.domains;

import java.util.Map;

public interface IAnimalDataMapper {
    Animal get (String animalId);
    void update (Animal animal);
    void insert (Animal animal);
    void delete (Animal animal);

    Map<String, Animal> getAnimalList();
}
