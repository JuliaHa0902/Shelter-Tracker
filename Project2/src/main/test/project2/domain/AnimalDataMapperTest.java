package project2.domain;
import org.junit.jupiter.api.Test;
import project2.domain.Animal;
import project2.domain.AnimalDataMapper;

import static org.junit.jupiter.api.Assertions.*;

class AnimalDataMapperTest {

    @Test
    void get() {
        AnimalDataMapper animalDataMapper = new AnimalDataMapper();
        Animal animal = animalDataMapper.get("12345");
        assertEquals (animal.getName(), "Happy");
    }

    @Test
    void insert() {
    }
}