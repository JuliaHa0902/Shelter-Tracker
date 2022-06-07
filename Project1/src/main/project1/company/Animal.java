package main.project1.company;

import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Class Animal contains information of an animal
 *
 * Project 1
 * Class ICS 372
 */
public class Animal {
    private String name;
    private String animalId;
    private float weight;
    private String shelterId;
    private String animalType;
    private Date receiptDate;

    /**
     * Constructor of an animal
     * @param animalJson a JSONObject contains animal information
     *
     */
    public Animal (JSONObject animalJson) {
        this.name = (String) animalJson.get("animal_name");
        this.receiptDate = new Date((long) animalJson.get("receipt_date"));
        this.animalId = (String) animalJson.get("animal_id");
        String weightString = String.valueOf(animalJson.get("weight"));
        this.weight = Float.parseFloat(weightString);
        this.shelterId = (String) animalJson.get("shelter_id");
        this.animalType = (String) animalJson.get("animal_type");
    }

    /**
     * Set ID
     * @param animalId new ID for this animal
     *
     */
    public void setId (String animalId) {
        this.animalId = animalId;
    }

    /**
     * Return current ID
     * @return animalId
     *
     */
    public String getId () {
        return animalId;
    }

    /**
     * Set shelter ID
     * @param shelterId new shelter ID for this animal
     *
     */
    public void setShelterId (String shelterId) {
        this.shelterId = shelterId;
    }

    /**
     * Return current shelter ID
     * @return shelterId
     *
     */
    public String getShelterId () {
        return shelterId;
    }

    /**
     * Set name
     * @param name new name for this animal
     *
     */
    public void setName (String name) {
        this.name = name;
    }

    /**
     * Return current name
     * @return name
     *
     */
    public String getName () {
        return name;
    }

    /**
     * Set receipt date
     * @param receiptDate new receipt date for this animal
     *
     */
    public void setReceiptDate (Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    /**
     * Return current receipt date
     * @return receiptDate
     *
     */
    public Date getReceiptDate () {
        return receiptDate;
    }

    /**
     * Set weight
     * @param weight new weight for this animal
     *
     */
    public void setWeight (float weight) {
        this.weight = weight;
    }

    /**
     * Return current weight
     * @return weight
     *
     */
    public float getWeight () {
        return weight;
    }

    /**
     * Set animal type
     * @param animalType new type for this animal
     *
     */
    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    /**
     * Return current animal type
     * @return animalType
     *
     */
    public String getAnimalType() {
        return animalType;
    }
}
