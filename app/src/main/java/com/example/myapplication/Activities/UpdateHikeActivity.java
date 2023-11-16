package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.HikeDatabase;
import com.example.myapplication.DatePicker.DatePickerDialogUpdate;
import com.example.myapplication.Models.Hike;
import com.example.myapplication.R;

import java.util.Locale;

public class UpdateHikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hike);


        // Lấy dữ liệu từ Intent
        long hikeId = getIntent().getLongExtra("hike_id", -1);

        // Lấy thông tin hike từ ID và hiển thị chúng trong form cập nhật
        HikeDatabase hikeDatabase = Room.databaseBuilder(getApplicationContext(), HikeDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries()
                .build();
        Hike hike = hikeDatabase.hikeDao().getHikeById(hikeId);



        // Hiển thị thông tin trong form
        EditText updateName = findViewById(R.id.updateName);
        EditText updateLocation = findViewById(R.id.updateLocation);
        EditText updateDescription = findViewById(R.id.updateDescription);
        EditText updateLength = findViewById(R.id.updateLength);

        TextView updateDate = findViewById(R.id.updateDate);

        RadioGroup updateRadioGroupParking = findViewById(R.id.updateRadioGroupParking);
        RadioButton checkYes = findViewById(R.id.checkYesNew);
        RadioButton checkNo = findViewById(R.id.checkNoNew);
        boolean parkingAvailable = hike.parkingAvailable;
        if (parkingAvailable) {
            checkYes.setChecked(true);
        } else {
            checkNo.setChecked(true);
        }


        Spinner updateSpinnerLevel = findViewById(R.id.updateSpinnerLevel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.levels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updateSpinnerLevel.setAdapter(adapter);
        //
        int position = adapter.getPosition(hike.level);
        updateSpinnerLevel.setSelection(position);


        updateName.setText(hike.name);
        updateLocation.setText(hike.location);
        updateDate.setText(hike.date);
        updateLength.setText(hike.length);
        updateDescription.setText(hike.description);

        //update
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ EditText và cập nhật vào cơ sở dữ liệu
                String newName = updateName.getText().toString();
                String newLocation = updateLocation.getText().toString();
                String newDate = updateDate.getText().toString();
                String newDescription = updateDescription.getText().toString();
                String newLength = updateLength.getText().toString();
                String newSpinnerLevel = updateSpinnerLevel.getSelectedItem().toString();

                int selectedRadioButtonId = updateRadioGroupParking.getCheckedRadioButtonId();
                boolean parkingAvailable = (selectedRadioButtonId == R.id.checkYesNew);


                hike.name = newName;
                hike.location = newLocation;
                hike.date = newDate;
                hike.length = newLength;
                hike.level = newSpinnerLevel;
                hike.description = newDescription;
                hike.parkingAvailable = parkingAvailable;

                // Cập nhật
                hikeDatabase.hikeDao().updateHike(hike);

                Toast.makeText(UpdateHikeActivity.this, "Hike updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void showDatePickerDialogUpdate(View v){
        DialogFragment newFragment = new DatePickerDialogUpdate();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateDateView(int year, int month, int dayOfMonth) {
        TextView updateDate = findViewById(R.id.updateDate);
        updateDate.setText(String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth));
    }
}
