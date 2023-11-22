package com.example.myapplication.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.HikeDatabase;
import com.example.myapplication.DatePicker.DatePickerDialogFragment;
import com.example.myapplication.Models.Hike;
import com.example.myapplication.R;
import com.example.myapplication.dao.HikeDao;

import java.time.LocalDate;
import java.util.Locale;

public class AddFragment extends Fragment {

    private View view;
    private HikeDatabase hikeDatabase;
    public AddFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        hikeDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), HikeDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        view = rootView;
// Spinner
        Spinner spinnerLevel = view.findViewById(R.id.spinnerLevel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.levels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(adapter);
//date
        TextView Date = view.findViewById(R.id.Date);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        EditText editName = view.findViewById(R.id.editName);
        EditText editLocation = view.findViewById(R.id.editLocation);
        EditText editDescription = view.findViewById(R.id.editDescription);
        EditText editLength = view.findViewById(R.id.editLength);
        RadioGroup radioGroupParking = view.findViewById(R.id.radioGroupParking);
//
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveToDatabase();
                String name = editName.getText().toString();
                String location = editLocation.getText().toString();
                String date = Date.getText().toString();
                String description = editDescription.getText().toString();
                String length = editLength.getText().toString();
                String selectedLevel = spinnerLevel.getSelectedItem().toString();

                int selectedRadioButtonId = radioGroupParking.getCheckedRadioButtonId();
                boolean parkingAvailable = (selectedRadioButtonId == R.id.checkYes);

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location) || TextUtils.isEmpty(length) || TextUtils.isEmpty(selectedLevel)) {
                    Toast.makeText(requireContext(), "Please enter all required information", Toast.LENGTH_SHORT).show();
                } else {

                    Hike hike = new Hike();
                    hike.name = name;
                    hike.location = location;
                    hike.date = date;
                    hike.length = length;
                    hike.level = selectedLevel;
                    hike.description = description;
                    hike.parkingAvailable = parkingAvailable;

                    displayNextAlert(hike);
                }
            }
        });

        return rootView;
    }


    private void showDatePickerDialog() {
        DatePickerDialogFragment datePickerFragment = new DatePickerDialogFragment();
        datePickerFragment.show(getChildFragmentManager(), "datePicker");
    }

    public void updateDateTextView(int year, int month, int day) {
        TextView Date = view.findViewById(R.id.Date);
        String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", month + 1, day, year);
        Date.setText(formattedDate);
    }


    public void displayNextAlert(Hike hike) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Do you want to create this Hike?");
        builder .setMessage(
                "Details \n" +
                        "\nName: " + hike.name + "\n" +
                        "\nLocation: " +  hike.location + "\n" +
                        "\nDate: " +  hike.date + "\n" +
                        "\nParking available: " +  (hike.parkingAvailable ? "yes":"No") + "\n" +
                        "\nLength of the hike: " +  hike.length + "\n" +
                        "\nDifficulty Level: " +  hike.level + "\n" +
                        "\nDescription: " +  hike.description + "\n"
        );
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveDataToDatabase(hike);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void saveDataToDatabase(Hike hike) {
        long hike_id = hikeDatabase.hikeDao().insertHike(hike);
        Toast.makeText(requireContext(), "Hike has been saved with ID: " + hike_id, Toast.LENGTH_LONG).show();
    }

}