package edu.metrostate.sheltertracker.controller;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.metrostate.sheltertracker.databinding.ActivityAnimalBinding;

import edu.metrostate.sheltertracker.R;

public class AnimalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        Bundle extras = getIntent().getExtras();
        String animalId;
        TextView tvAnimalId = findViewById(R.id.tvAnimalId);

        if (extras != null) {
            animalId = extras.getString("animalId");
            tvAnimalId.setText("Animal " + animalId);
        } else {
            tvAnimalId.setText("Invalid Animal");
        }
    }

    public void backHome (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}