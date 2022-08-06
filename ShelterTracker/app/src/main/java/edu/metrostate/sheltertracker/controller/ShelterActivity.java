package edu.metrostate.sheltertracker.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.metrostate.sheltertracker.R;
import edu.metrostate.sheltertracker.domains.Animal;
import edu.metrostate.sheltertracker.domains.AnimalAdapter;
import edu.metrostate.sheltertracker.domains.Shelter;
import edu.metrostate.sheltertracker.domains.ShelterTrackerApplication;

public class ShelterActivity extends AppCompatActivity {
    Shelter shelter;
    TextView tvShelterId;
    TextView tvShelterName;
    Switch swIntaking;
    ListView lvAnimalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);
        Bundle extras = getIntent().getExtras();
        tvShelterId = findViewById(R.id.tvShelterId);
        tvShelterName = findViewById(R.id.tvShelterName);
        swIntaking = findViewById(R.id.swInTaking);
        lvAnimalList = findViewById(R.id.lvAnimalList);

        if (extras != null) {
            String shelterId = extras.getString("shelterId");
            shelter = ((ShelterTrackerApplication)getApplication()).getShelterInfo(shelterId);
            if (shelter == null) {
                setInvalid();
            } else {
                tvShelterName.setText("Shelter " + shelter.getShelterName());
                tvShelterId.setText("Shelter ID: " + shelter.getShelterId());
                swIntaking.setChecked(shelter.getInTaking());
                swIntaking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (swIntaking.isChecked()) {
                            ((ShelterTrackerApplication)getApplication()).enableReceivingAnimal(shelter.getShelterId());
                        } else {
                            ((ShelterTrackerApplication)getApplication()).disableReceivingAnimal(shelter.getShelterId());
                        }
                    }
                });
                Log.i ("Shelter", shelter.getInTaking().toString());
                List<Animal> animalList = new ArrayList<>(shelter.getAnimalList().values());
                lvAnimalList.setAdapter(new AnimalAdapter(this, animalList));
                lvAnimalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.i("itemClick", String.valueOf(i));
                        Animal animal = animalList.get(i);
                        Intent intent = new Intent(ShelterActivity.this, AnimalActivity.class);
                        intent.putExtra("animalId", animal.getAnimalId());
                        startActivity(intent);
                    }
                });
            }
        } else {
            setInvalid();
        }
    }

    public void setInvalid () {
        tvShelterName.setText("Invalid Shelter");
        swIntaking.setVisibility(View.GONE);
    }

    public void backShelterList (View view) {
        Intent intent = new Intent(this, ShelterListActivity.class);
        startActivity(intent);
    }

    public void backHome (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toggleInTaking(View view) {
//        Log.i("toggle shelter", String.valueOf(swIntaking.isChecked()));
        if (swIntaking.isChecked()) {
            ((ShelterTrackerApplication)getApplication()).enableReceivingAnimal(shelter.getShelterId());
        } else {
            ((ShelterTrackerApplication)getApplication()).disableReceivingAnimal(shelter.getShelterId());
        }
    }
}