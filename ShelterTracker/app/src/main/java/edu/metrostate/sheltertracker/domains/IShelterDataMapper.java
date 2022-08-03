package edu.metrostate.sheltertracker.domains;

import java.util.Map;

public interface IShelterDataMapper {
    Shelter get (String shelterId);
    void update(Shelter shelter);
    void insert (Shelter shelter);
    void delete (String shelterId);
    public Map<String, Shelter> getShelterList ();
}
