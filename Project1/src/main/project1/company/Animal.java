package main.project1.company;

import org.json.simple.JSONObject;

import java.util.Date;

public class Animal {
    private String name;
    private String animalId;
    private long weight;
    private String shelterId;
    private String animalType;
    private Date receiptDate;

    public Animal (JSONObject animalJson) {
        this.name = (String) animalJson.get("animal_name");
        this.receiptDate = new Date((long) animalJson.get("receipt_date"));
        this.animalId = (String) animalJson.get("animal_id");
        this.weight = (long) animalJson.get("weight");
        this.shelterId = (String) animalJson.get("shelter_id");
        this.animalType = (String) animalJson.get("animal_type");
    }

    public void setId (String animalId) {
        this.animalId = animalId;
    }

    public String getId () {
        return animalId;
    }

    public void setShelterId (String shelterId) {
        this.shelterId = shelterId;
    }
    public String getShelterId () {
        return shelterId;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public void setReceiptDate (Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Date getReceiptDate () {
        return receiptDate;
    }

    public void setWeight (long weight) {
        this.weight = weight;
    }

    public long getWeight () {
        return weight;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getAnimalType() {
        return animalType;
    }
}
