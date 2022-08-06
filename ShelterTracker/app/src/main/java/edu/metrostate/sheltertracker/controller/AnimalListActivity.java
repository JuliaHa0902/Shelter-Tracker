package edu.metrostate.sheltertracker.controller;
import edu.metrostate.sheltertracker.R;
import edu.metrostate.sheltertracker.domains.Animal;
import edu.metrostate.sheltertracker.domains.AnimalAdapter;
import edu.metrostate.sheltertracker.domains.Shelter;
import edu.metrostate.sheltertracker.domains.ShelterAdapter;
import edu.metrostate.sheltertracker.domains.ShelterTrackerApplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AnimalListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_list);
        ListView lv = findViewById(R.id.animalList);
        lv.setAdapter(new AnimalAdapter(this,
                ((ShelterTrackerApplication)getApplication()).getAnimalList()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("itemClick", String.valueOf(i));
                Animal animal = ((ShelterTrackerApplication)getApplication()).getAnimalList().get(i);
                Intent intent = new Intent(AnimalListActivity.this, AnimalActivity.class);
                intent.putExtra("animalId", animal.getAnimalId());
                startActivity(intent);
            }
        });
    }
    public void addAnimal (View view) {
        Intent intent = new Intent(this, AddAnimalActivity.class);
        startActivity(intent);
    }

    public void backHome (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}