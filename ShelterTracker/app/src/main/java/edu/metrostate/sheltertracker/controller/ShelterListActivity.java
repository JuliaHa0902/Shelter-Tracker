package edu.metrostate.sheltertracker.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

    private static final int FAILED = -1;

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

    public void exportJSON(View view) {
        int result = ((ShelterTrackerApplication)getApplication()).exportAnimalList("JSON");
        if (result == FAILED) {
            showMessage ("Export JSOM failed");
        } else {
            showMessage("Export JSON success. Please look for file shelterExport");
        }
    }

    public void exportXML(View view) {
        int result = ((ShelterTrackerApplication)getApplication()).exportAnimalList("XML");
        if (result == FAILED) {
            showMessage ("Export XML failed");
        } else {
            showMessage("Export XML success. Please look for file shelterExport");
        }
    }
    public void showMessage (String message) {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("Export animals").setCancelable(false)
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
}