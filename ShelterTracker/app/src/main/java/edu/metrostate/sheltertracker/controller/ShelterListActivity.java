package edu.metrostate.sheltertracker.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import edu.metrostate.sheltertracker.R;
import edu.metrostate.sheltertracker.domains.Shelter;
import edu.metrostate.sheltertracker.domains.ShelterAdapter;
import edu.metrostate.sheltertracker.domains.ShelterTrackerApplication;

public class ShelterListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);

        ListView lv = findViewById(R.id.shelter_list);

        lv.setAdapter(new ShelterAdapter(this,
                ((ShelterTrackerApplication)getApplication()).getShelterList()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("itemClick", String.valueOf(i));
                Shelter shelter = ((ShelterTrackerApplication)getApplication()).getShelterList().get(i);
                Intent intent = new Intent(ShelterListActivity.this, ShelterActivity.class);
                intent.putExtra("shelterId", shelter.getShelterId());
                startActivity(intent);
            }
        });
    }
}