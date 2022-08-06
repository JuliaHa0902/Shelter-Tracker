package edu.metrostate.sheltertracker.domains;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XmlIO implements IDataIO{
    Context context;
    public XmlIO(Context context) {
        this.context = context;
    }

    /**
     * Read xml file in path
     * return an array list of Shelter
     */
    @Override
    public Map<String, Shelter> convert(String fileName) throws IOException, ParseException, FileNotFoundException {
        String filePath = MyPath.getResourcePath(this.context, fileName) + ".xml";
        try {
            File file = new File(filePath);

            DocumentBuilderFactory dbf  = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Shelter");

            Map<String, Shelter> shelterList = new HashMap<>();

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node shelterNode = nodeList.item(i);
                if (shelterNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element shelterElement = (Element)shelterNode;

                    String shelterName = shelterElement.getElementsByTagName("Name").item(0).getTextContent();
                    String shelterId = shelterElement.getAttribute("id");
                    if (shelterList.get(shelterId) == null) {
                        shelterList.put(shelterId, new Shelter(shelterId, shelterName));
                    } //Should check for different shelter name here

                    NodeList animalNodeList = shelterElement.getElementsByTagName("Animal");
                    for (int j = 0; j < animalNodeList.getLength(); j++) {
                        Node animalNode = animalNodeList.item(j);
                        if (animalNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element animalElement = (Element) animalNode;
                            Animal animal = new Animal(animalElement, shelterId);
                            shelterList.get(shelterId).addAnimal(animal);
                        }
                    }
                }
            }
            return shelterList;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Export Shelter information to a xml file, placed in resources
     */
    @Override
    public void dataExport(Map<String, Shelter> shelterList) throws FileNotFoundException {
        String filePath = MyPath.getResourcePath(this.context, "shelterExport.xml");
        PrintWriter pw = new PrintWriter(filePath);
        pw.println("<Shelters>");
        for (String key : shelterList.keySet()) {
            Shelter shelter = shelterList.get(key);
            pw.printf("<Shelter id=\"%s\">\n", shelter.getShelterId());
            pw.printf("<Name>%s</Name>\n", shelter.getShelterName());
            Map<String, Animal> animalList = shelter.getAnimalList();
            for (String animalId : animalList.keySet()) {
                Animal animal = animalList.get(animalId);
                pw.printf("   <Animal type = \"%s\" id=\"%s\">\n", animal.getAnimalType(), animalId);
                pw.printf("      <Name>%s</Name>\n", animal.getName());
                pw.printf("      <Weight>%.2f</Weight>\n", animal.getWeight());
                pw.printf("      <ReceiptDate>%s</ReceiptDate>\n", animal.getReceiptDate().getTime());
                pw.println("   </Animal>");
            }

            pw.println("</Shelter>");
        }
        pw.println("</Shelters>");
        pw.flush();
        pw.close();
    }
}
