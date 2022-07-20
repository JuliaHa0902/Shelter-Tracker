package project2.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalDataMapperTest {

    @Test
    public void get() {
        AnimalDataMapper animalDataMapper = new AnimalDataMapper();
        Animal animal = animalDataMapper.get("12345");
        assertEquals(animal.getName(), "Happy");
    }

}