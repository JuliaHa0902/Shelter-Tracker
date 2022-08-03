package edu.metrostate.sheltertracker.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.metrostate.sheltertracker.R;

public class ShelterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);
        Bundle extras = getIntent().getExtras();
        String shelterId;
        TextView tvAnimalId = findViewById(R.id.tvShelterId);

        if (extras != null) {
            shelterId = extras.getString("shelterId");
            tvAnimalId.setText("Shelter " + shelterId);
        } else {
            tvAnimalId.setText("Invalid Shelter");
        }
    }

    public void backShelterList (View view) {
        Intent intent = new Intent(this, ShelterListActivity.class);
        startActivity(intent);
    }

    public void backHome (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}