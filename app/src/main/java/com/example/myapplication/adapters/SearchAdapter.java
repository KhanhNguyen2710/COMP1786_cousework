package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.Hike;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Hike> originalHikes; // ds gốc
    private List<Hike> filteredHikes; // Danh sách hiển thị (có thể bị lọc)

    public SearchAdapter(List<Hike> hikes) {
        this.originalHikes = hikes;
        this.filteredHikes = new ArrayList<>(hikes);
    }

    public void filterList(List<Hike> filteredList) {
        filteredHikes = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Hike hike = filteredHikes.get(position); //
        holder.search_name.setText(hike.name);
    }

    @Override
    public int getItemCount() {
        return filteredHikes.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView search_name;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            search_name = itemView.findViewById(R.id.search_name);
        }
    }
}