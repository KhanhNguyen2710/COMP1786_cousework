package com.example.myapplication.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

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
        // Required empty public constructor
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

//        TextView Date = view.findViewById(R.id.Date);
        TextView Date = view.findViewById(R.id.Date);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở DatePickerDialogFragment khi nhấp vào TextView Date
                showDatePickerDialog();
            }
        });

//
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
// lấy văn bản
                String name = editName.getText().toString();
                String location = editLocation.getText().toString();
                String date = Date.getText().toString();
                String description = editDescription.getText().toString();
                String length = editLength.getText().toString();
                String selectedLevel = spinnerLevel.getSelectedItem().toString();

                int selectedRadioButtonId = radioGroupParking.getCheckedRadioButtonId();
                boolean parkingAvailable = (selectedRadioButtonId == R.id.checkYes);


                Hike hike = new Hike();
                hike.name= name;
                hike.location= location;
                hike.date= date;
                hike.length= length;
                hike.level = selectedLevel;
                hike.description= description;
                hike.parkingAvailable= parkingAvailable;

                // Set properties of the Hike object based on the user input

                long hike_id = hikeDatabase.hikeDao().insertHike(hike);

                Toast.makeText(requireContext(), "Hike has been created with id: " + hike_id,
                        Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }


    private void showDatePickerDialog() {
        DatePickerDialogFragment datePickerFragment = new DatePickerDialogFragment();
        datePickerFragment.show(getChildFragmentManager(), "datePicker");
    }

    // Cập nhật TextView Date sau khi chọn ngày
    public void updateDateTextView(int year, int month, int day) {
        TextView Date = view.findViewById(R.id.Date);
        String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", month + 1, day, year);
        Date.setText(formattedDate);
    }



}