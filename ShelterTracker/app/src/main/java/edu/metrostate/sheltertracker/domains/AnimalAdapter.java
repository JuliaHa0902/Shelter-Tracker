package edu.metrostate.sheltertracker.domains;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.metrostate.sheltertracker.R;
import edu.metrostate.sheltertracker.controller.AnimalListActivity;

public class AnimalAdapter extends ArrayAdapter<Animal> {

    public AnimalAdapter(Context context, List<Animal> animalList) {
        super(context, R.layout.animal_list_item,animalList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.animal_list_item, parent, false);
        }

        TextView id = convertView.findViewById(R.id.tvAnimalId);
        TextView name = convertView.findViewById(R.id.tvAnimalName);

        id.setText(getItem(position).getAnimalId());
        name.setText(getItem(position).getName());

        return convertView;
    }
}
