package project2.domain;

import org.json.simple.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Date;


/**
 * Class Animal contains information of an animal
 * Emma
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
     *
     * @param animalJSON a JSONObject contains animal information
     */
    public Animal(JSONObject animalJSON) {
        this.name = (String) animalJSON.get("animal_name");
        this.animalId = (String) animalJSON.get("animal_id");
        String weightString = String.valueOf(animalJSON.get("weight"));
        this.weight = Float.parseFloat(weightString);
        this.shelterId = (String) animalJSON.get("shelter_id");
        this.animalType = (String) animalJSON.get("animal_type");
        this.receiptDate = new Date((long) animalJSON.get("receipt_date"));
    }

    public Animal(Element animalXML, String shelterId) {
        this.animalId = animalXML.getAttribute("id");
        this.animalType = animalXML.getAttribute("type");
        this.name = animalXML.getElementsByTagName("Name").item(0).getTextContent();
        String dateString = "0";
        if (animalXML.getElementsByTagName("ReceiptDate").item(0) != null) {
            dateString = animalXML.getElementsByTagName("ReceiptDate").item(0).getTextContent();
        } else {
            System.out.println("You forgot to put receipt date string! T_____T");
        }
        this.receiptDate = new Date(Long.parseLong(dateString));
        this.weight = Float.parseFloat(animalXML.getElementsByTagName("Weight").item(0).getTextContent());
        Element elementWeight = (Element) animalXML.getElementsByTagName("Weight").item(0);
        String unit = elementWeight.getAttribute("unit");
        if (unit.equals("lb")) {
            this.weight = this.weight * (float)0.45;
        }
        this.shelterId = shelterId;
    }

    public Animal (String name, String animalId, float weight, String shelterId, String animalType, Date receiptDate) {
        this.name = name;
        this.animalId = animalId;
        this.weight = weight;
        this.shelterId = shelterId;
        this.animalType = animalType;
        this.receiptDate = receiptDate;
    }


    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }


    public String getAnimalId() {
        return animalId;
    }


    public void setShelterId(String shelterId) {
        this.shelterId = shelterId;
    }


    public String getShelterId() {
        return shelterId;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }



    public Date getReceiptDate() {
        return receiptDate;
    }


    public void setWeight(float weight) {
        this.weight = weight;
    }


    public float getWeight() {
        return weight;
    }



    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }



    public String getAnimalType() {
        return animalType;
    }

} //end of animal class



