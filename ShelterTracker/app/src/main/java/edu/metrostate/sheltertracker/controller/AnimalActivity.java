package edu.metrostate.sheltertracker.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Date;


import edu.metrostate.sheltertracker.R;
import edu.metrostate.sheltertracker.domains.Animal;
import edu.metrostate.sheltertracker.domains.Shelter;
import edu.metrostate.sheltertracker.domains.ShelterTrackerApplication;

public class AnimalActivity extends AppCompatActivity {
    public String animalId;
    TextView tvAnimalName;
    TextView tvAnimalInfo;
    Button btDeleteAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        Bundle extras = getIntent().getExtras();

        this.tvAnimalName = findViewById(R.id.tvAnimalName);
        this.tvAnimalInfo = findViewById(R.id.tvAnimalInfo);
        this.btDeleteAnimal = findViewById(R.id.btDeleteAnimal);

        if (extras != null) {
            animalId = extras.getString("animalId");
            Animal animal = ((ShelterTrackerApplication)getApplication()).getAnimalInfo(animalId);

            if (animal == null) {
                tvAnimalName.setText("Invalid Animal");
                btDeleteAnimal.setVisibility(View.GONE);
            } else {
                Shelter shelter = ((ShelterTrackerApplication)getApplication()).getShelterInfo(animal.getShelterId());
                tvAnimalName.setText("Animal " + animal.getName());
                String shelterName;
                if (shelter == null) {
                    shelterName = "N/A";
                } else {
                    shelterName = shelter.getShelterName();
                }
                String animalInfo = getAnimalInfo(animal, shelterName);
                tvAnimalInfo.setText(animalInfo);
            }
        } else {
            tvAnimalName.setText("Invalid Animal");
            btDeleteAnimal.setVisibility(View.GONE);
        }
    }

    public String getAnimalInfo (Animal animal, String shelterName) {
        String animalInfo = String.format("Animal id: %s \nShelter id: %s\nShelter name: %s\n",
                animal.getAnimalId(), animal.getShelterId(), shelterName);
        Date releaseDate = animal.getReleaseDate();
        String releaseString = "";
        if (releaseDate.equals(new Date(0))) {
            releaseString = "N/A";
        } else releaseString = releaseDate.toString();
        animalInfo += String.format("Type: %s\nWeight:%.2f\nReceipt Date: %s\n Release Date: %s\n",
                animal.getAnimalType(), animal.getWeight(), animal.getReceiptDate(), releaseString);
        return animalInfo;
    }

    public void releaseAnimal (View view) {
        int result = ((ShelterTrackerApplication)getApplication()).removeAnimal(animalId);
        String message = "";
        if (result == -1) {
            message = "Animal has already left the shelter!";
        } else {
            message = "Release success!";
            Animal animal = ((ShelterTrackerApplication)getApplication()).getAnimalInfo(animalId);
            Shelter shelter = ((ShelterTrackerApplication)getApplication()).getShelterInfo(animal.getShelterId());
            String animalInfo;
            if (shelter != null) {
                animalInfo = getAnimalInfo(animal, shelter.getShelterName());
            } else {
                animalInfo = getAnimalInfo(animal, "N/A");
            }
            tvAnimalInfo.setText(animalInfo);
        }

        Dialog dialog = new AlertDialog.Builder(this).setTitle("Release Animal").setCancelable(false)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).create();

        dialog.show();
    }

    public void backHome (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void backAnimalList(View view) {
        Intent intent = new Intent(this, AnimalListActivity.class);
        startActivity(intent);
    }
}