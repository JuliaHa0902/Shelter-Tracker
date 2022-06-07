package main.project1.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.project1.company.Animal;
import main.project1.company.PetCompany;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
    private PetCompany company;

    public UserInterface () {
        company = new PetCompany("Project1_input.json");
    }

    public void showMenu () {
        System.out.println("Welcome to our Project 1: Pet adoption company app");
        System.out.println("We have already loaded the input file in the system");
        System.out.println("Please select the following options to interact with the app:");
        System.out.println(" - Type 1 to add new animal to our shelter");
        System.out.println(" - Type 2 to enable receiving animal for a shelter");
        System.out.println(" - Type 3 to disable receiving animal for a shelter");
        System.out.println(" - Type 4 to export all animals from a shelter into a single JSON file");
        System.out.println(" - Type 5 to show the list of current animals for each shelter");
        System.out.println(" - Type 0 to quit");
        System.out.println("Your option is: ");
    }

    public void addAnimal () {
        System.out.println("Please notice that all input file should be placed under resources folder");
        System.out.println("Please notice that the format of the file should be similar to the format of the example JSON input file");
        System.out.println("Please enter the name of the file that contains the animal information:");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        try {
            company.addAnimal(fileName);
            System.out.println("Done adding animal");
        } catch (IOException e) {
            System.out.println("Exception IOException");
            System.out.println ("Please make sure that your input file is put under resources folder");
            System.out.println ("and the name is typed correctly (include extension)");
        } catch (ParseException e) {
            System.out.println("Exception Parse Exception");
            System.out.println ("Please make sure that the file contains JSON data");
        } catch (ClassCastException e) {
            System.out.println("Class Cast Exception");
            System.out.println ("Please make sure that the format of the file is similar to the format of the example input file");
        }
    }

    public void enableReceivingAnimal () {
        System.out.println ("Please enter the shelter ID that you want to enable receiving animal");
        Scanner scanner = new Scanner(System.in);
        String shelterId = scanner.nextLine();
        int result = company.enableReceivingAnimal(shelterId);
        if (result == 0) {
            System.out.printf("Successfully enable receiving animal for shelter %s \n", shelterId);
        } else {
            System.out.printf("Invalid shelter ID %s \n", shelterId);
        }
    }

    public void disableReceivingAnimal () {
        System.out.println ("Please enter the shelter ID that you want to disable receiving animal");
        Scanner scanner = new Scanner(System.in);
        String shelterId = scanner.nextLine();
        int result = company.disableReceivingAnimal(shelterId);
        if (result == 0) {
            System.out.printf("Successfully disable receiving animal for shelter %s \n", shelterId);
        } else {
            System.out.printf("Invalid shelter ID %s \n", shelterId);
        }
    }

    public void exportAnimalListJSON () {
        System.out.println ("Please enter the shelter ID that you want to export all animals into a single JSON file");
        Scanner scanner = new Scanner(System.in);
        String shelterId = scanner.nextLine();
        JSONObject animalListJSON = company.exportAnimalListJSON(shelterId);
        if (animalListJSON == null) {
            System.out.println("Invalid shelterId. Unable to export animal list.");
            return;
        }
        System.out.println("Here is the JSON file");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println( gson.toJson(animalListJSON));
    }

    public void showAnimalList () {
        System.out.println ("Please enter the shelter ID that you want to show the list of current animals");
        Scanner scanner = new Scanner(System.in);
        String shelterId = scanner.nextLine();
        Map<String, Animal> animalList = company.showAnimalList(shelterId);
        if (animalList == null) {
            System.out.println("Invalid shelterId. Unable to show animal list.");
            return;
        }
        for (String key : animalList.keySet()) {
            System.out.printf("Animal id %s \n", animalList.get(key).getId());
            System.out.printf("   Name: %s \n", animalList.get(key).getName());
            System.out.printf("   Type: %s \n", animalList.get(key).getAnimalType());
            System.out.printf("   Weight: %s \n", animalList.get(key).getWeight());
            System.out.printf("   Receipt Date: %s \n", animalList.get(key).getReceiptDate());
            System.out.printf("   Shelter ID: %s \n", animalList.get(key).getShelterId());
        }
    }

    public static void main (String[] args) {
        UserInterface ui = new UserInterface();
        boolean isRunning = true;
        while (isRunning) {
            ui.showMenu();
            try {
                Scanner scanner = new Scanner(System.in);
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        ui.addAnimal();
                        break;
                    case 2:
                        ui.enableReceivingAnimal();
                        break;
                    case 3:
                        ui.disableReceivingAnimal();
                        break;
                    case 4:
                        ui.exportAnimalListJSON();
                        break;
                    case 5:
                        ui.showAnimalList();
                        break;
                    case 0:
                        isRunning = false;
                        System.out.println("The program will shut down!");
                        break;
                    default:
                        System.out.println("Your option is not supported");
                        break;
                }
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Your option is not supported");
                System.out.println("Press Enter to continue...");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
            }
        }
    }
}
