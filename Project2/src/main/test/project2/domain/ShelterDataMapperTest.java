package project2.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShelterDataMapperTest {

    @Test
    public void get() {
        ShelterDataMapper shelterDataMapper = new ShelterDataMapper();
        Shelter shelter = shelterDataMapper.get("54321");
        assertEquals(shelter.getName(), "Test Shelter");
    }
}