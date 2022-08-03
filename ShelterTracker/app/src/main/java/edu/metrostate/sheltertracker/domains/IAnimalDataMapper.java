package edu.metrostate.sheltertracker.domains;

public interface IAnimalDataMapper {
    Animal get (String animalId);
    void update (Animal animal);
    void insert (Animal animal);
    void delete (String animalId);
}
