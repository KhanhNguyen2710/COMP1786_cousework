package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.Database.HikeDatabase;
import com.example.myapplication.Models.Hike;
import com.example.myapplication.R;
import com.example.myapplication.dao.HikeDao;

public class AddFragment extends Fragment {
    private View view;
    private HikeDatabase hikeDatabase;
    public AddFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        hikeDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), HikeDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();



        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveToDatabase();
                EditText editName = view.findViewById(R.id.editName);
                EditText editLocation = view.findViewById(R.id.editLocation);
                EditText editDate = view.findViewById(R.id.editDate);
                EditText editDescription = view.findViewById(R.id.editDescription);
                EditText editLength = view.findViewById(R.id.editLength);
                EditText editLevel = view.findViewById(R.id.editLevel);
                RadioGroup radioGroupParking = view.findViewById(R.id.radioGroupParking);

                String name = editName.getText().toString();
                String location = editLocation.getText().toString();
                String date = editDate.getText().toString();
                String description = editDescription.getText().toString();
                String length = editLength.getText().toString();
                String level = editLevel.getText().toString();
                int selectedRadioButtonId = radioGroupParking.getCheckedRadioButtonId();
                boolean parkingAvailable = (selectedRadioButtonId == R.id.checkYes);

                Hike hike = new Hike();
                hike.name= name;
                hike.location= location;
                hike.date= date;
                hike.length= length;
                hike.level= level;
                hike.description= description;
                hike.parkingAvailable= parkingAvailable;

                // Set properties of the Hike object based on the user input

                long hike_id = hikeDatabase.hikeDao().insertHike(hike);

                Toast.makeText(requireContext(), "Hike has been created with id: " + hike_id,
                        Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
//    private void saveToDatabase() {
//        EditText editName = view.findViewById(R.id.editName);
//        EditText editLocation = view.findViewById(R.id.editLocation);
//        EditText editDate = view.findViewById(R.id.editDate);
//        EditText editDescription = view.findViewById(R.id.editDescription);
//        EditText length = view.findViewById(R.id.length);
//        EditText level = view.findViewById(R.id.level);
//        RadioGroup radioGroupParking = view.findViewById(R.id.radioGroupParking);
//
//        String name = editName.getText().toString();
//        String location = editLocation.getText().toString();
//        String date = editDate.getText().toString();
//        String description = editDescription.getText().toString();
//        String hikeLength = length.getText().toString();
//        String hikeLevel = level.getText().toString();
//        int selectedRadioButtonId = radioGroupParking.getCheckedRadioButtonId();
//        boolean parkingAvailable = (selectedRadioButtonId == R.id.checkYes);
//
//        Hike hike = new Hike();
//        hike.name= name;
//        // Set properties of the Hike object based on the user input
//
//        long hike_id = hikeDatabase.hikeDao().insertHike(hike);
//
//        Toast.makeText(requireContext(), "Hike has been created with id: " + hike_id,
//                Toast.LENGTH_LONG).show();
//    }
}