package project2.domain;

public interface IAnimalDataMapper {
    Animal get (String animalId);
    void update (Animal animal);
    void insert (Animal animal);
    void delete (String animalId);
}
