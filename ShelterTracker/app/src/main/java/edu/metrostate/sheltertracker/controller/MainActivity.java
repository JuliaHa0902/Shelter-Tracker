package edu.metrostate.sheltertracker.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.metrostate.sheltertracker.R;

public class MainActivity extends AppCompatActivity {

    EditText etAnimalId;
    EditText etShelterId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAnimalId = findViewById(R.id.inAnimalId);
        etShelterId = findViewById(R.id.inShelterId);
    }

    public void searchAnimal (View view) {
//        Log.i ("MainActivity", "Animal: " + etAnimalId.getText().toString());
        Intent intent = new Intent(this, AnimalActivity.class);
        if (etAnimalId.getText().toString().equals("")) {
            Dialog dialog = new AlertDialog.Builder(this).setTitle("Empty animal ID").setCancelable(false)
                    .setMessage("Please enter animal ID")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    }).create();

            dialog.show();
        } else {
            intent.putExtra("animalId", etAnimalId.getText().toString());
            startActivity(intent);
        }

    }

    public void searchShelter (View view) {
        Intent intent = new Intent(this, ShelterActivity.class);
        if (etShelterId.getText().toString().equals("")) {
            Dialog dialog = new AlertDialog.Builder(this).setTitle("Empty shelter ID").setCancelable(false)
                    .setMessage("Please enter a shelter ID")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    }).create();

            dialog.show();
        } else {
            intent.putExtra("shelterId", etShelterId.getText().toString());
            startActivity(intent);
        }
    }

    public void showShelterList (View view) {
        Intent intent = new Intent(this, ShelterListActivity.class);
        startActivity(intent);
    }

    public void showAnimalList (View view) {
        Intent intent = new Intent(this, AnimalListActivity.class);
        startActivity(intent);
    }

}