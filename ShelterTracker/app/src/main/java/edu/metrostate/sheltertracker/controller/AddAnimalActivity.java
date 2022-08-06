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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.metrostate.sheltertracker.R;
import edu.metrostate.sheltertracker.domains.ShelterTrackerApplication;

public class AddAnimalActivity extends AppCompatActivity {
    private Spinner spFileType;
    private EditText etFileName;
    private String fileType = "JSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        etFileName = findViewById(R.id.etFileName);
        spFileType = findViewById(R.id.spFileType);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource
                (this, R.array.import_type, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFileType.setAdapter(arrayAdapter);
        spFileType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fileType = spFileType.getItemAtPosition(i).toString();
                Log.i ("file type", fileType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void importAnimal (View view) {
        try {
            String fileName = etFileName.getText().toString();
            if (fileName == null) showMessage("Please enter file name");
            String errorNote = ((ShelterTrackerApplication)getApplication()).addAnimal(fileName, fileType);
            Log.i ("file type", fileName + " "  + fileType);
            errorNote = "Import done!\n" + errorNote;
            showMessage(errorNote);
        } catch (FileNotFoundException e) {
            String errorNote = "Can't find file\n";
            showMessage(errorNote);
            e.printStackTrace();
        } catch (IOException e) {
            String errorNote = "Can't find file\n";
            showMessage(errorNote);
            e.printStackTrace();
        } catch (ParseException e) {
            String errorNote = "Invalid file content\n";
            showMessage(errorNote);
            e.printStackTrace();
        }
    }

    public void showMessage (String message) {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("Import animals").setCancelable(false)
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