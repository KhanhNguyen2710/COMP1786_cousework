package com.example.myapplication.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.Activities.UpdateHikeActivity;
import com.example.myapplication.Database.HikeDatabase;
import com.example.myapplication.Models.Hike;
import com.example.myapplication.R;
import com.example.myapplication.adapters.HikeAdapter;

import java.util.List;


public class HomeFragment extends Fragment implements HikeAdapter.OnDeleteClickListener, HikeAdapter.OnMoreClickListener {

    private HikeDatabase hikeDatabase;
    private View view;
    private RecyclerView recyclerView;
    private HikeAdapter adapter;

    List<Hike> hikes;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        view = rootView;

        hikeDatabase = Room.databaseBuilder(requireContext(), HikeDatabase.class, "sqlite_example_db")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        hikes = hikeDatabase.hikeDao().getAllHike();

        adapter = new HikeAdapter(hikes, this, this);
        recyclerView.setAdapter(adapter);

// delete all
        Button buttonDeleteAll = rootView.findViewById(R.id.buttonDeleteAll);
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAllConfirmationDialog();
            }
        });


        return rootView;

    }
    @Override
    public void onDeleteClick(int position) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Hike")
                .setMessage("Are you sure you want to delete this hike?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    Hike deletedHike = hikes.get(position);
                 hikeDatabase.hikeDao().deleteHike(deletedHike);
                 hikes.remove(position);
                 adapter.notifyItemRemoved(position);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    //  xóa tất cả dữ liệu
    private void showDeleteAllConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete All Data")
                .setMessage("Are you sure you want to delete all data?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    hikeDatabase.hikeDao().deleteAllHikes();
                    hikes.clear();
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onMoreClick(int position) {
        // Lấy thông tin hike được chọn
        Hike selectedHike = hikes.get(position);

        Intent intent = new Intent(getActivity(), UpdateHikeActivity.class);

        // Đặt dữ liệu cho Intent để truyền cho trang form mới
        intent.putExtra("hike_id", selectedHike.getId());

        startActivity(intent);
    }


}