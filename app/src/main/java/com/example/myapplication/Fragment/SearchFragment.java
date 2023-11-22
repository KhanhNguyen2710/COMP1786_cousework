package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.myapplication.Database.HikeDatabase;
import com.example.myapplication.Models.Hike;
import com.example.myapplication.R;
import com.example.myapplication.adapters.HikeAdapter;
import com.example.myapplication.adapters.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    HikeDatabase hikeDatabase;
    RecyclerView recyclerViewSearch;
    SearchAdapter adapter;
    EditText searchEditText;
    List<Hike> hikes;

    public SearchFragment() {
        //  empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        hikeDatabase = Room.databaseBuilder(requireContext().getApplicationContext(), HikeDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries()
                .build();

        searchEditText = rootView.findViewById(R.id.searchEditText);
        recyclerViewSearch = rootView.findViewById(R.id.recyclerViewSearch);

        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(requireContext()));
        hikes = hikeDatabase.hikeDao().getAllHike();

        adapter = new SearchAdapter(hikes);
        recyclerViewSearch.setAdapter(adapter);

        //  TextWatcher để lắng nghe sự kiện thay đổi
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //  loc
                filterHikes(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return rootView;
    }

    private void filterHikes(String searchText) {
        List<Hike> filteredList = new ArrayList<>();
        for (Hike hike : hikes) {
            // Lọc dựa trên tên chuyến đi
            if (hike.name.toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(hike);
            }
        }
        // Cập nhật  adapter
        adapter.filterList(filteredList);
    }
}