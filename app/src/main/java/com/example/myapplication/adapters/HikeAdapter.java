package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.Hike;
import com.example.myapplication.R;

import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder> {

    private List<Hike> hikes;
    private OnDeleteClickListener onDeleteClickListener;
    private OnMoreClickListener onMoreClickListener;



    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public interface OnMoreClickListener {
        void onMoreClick(int position);
    }

    public HikeAdapter(List<Hike> hikes , OnDeleteClickListener onDeleteClickListener,  OnMoreClickListener onMoreClickListener) {
        this.hikes = hikes;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onMoreClickListener = onMoreClickListener;

    }

    @NonNull
    @Override
    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.hike_list_item, parent, false);
        return new HikeViewHolder(view);
    }
//ch data
    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) {
        Hike hike = hikes.get(position);
        holder.hike_name.setText(hike.name);

        // Xử lý sự kiện nút xoá
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(position);
                }
            }
        });
        holder.buttonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMoreClickListener != null) {
                    onMoreClickListener.onMoreClick(position);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return hikes.size();
    }
// kb
    public static class HikeViewHolder extends RecyclerView.ViewHolder {
        TextView hike_name;
        Button buttonDelete, buttonMore;
        public HikeViewHolder(@NonNull View itemView) {
            super(itemView);
            hike_name = itemView.findViewById(R.id.hike_name);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonMore = itemView.findViewById(R.id.buttonMore);
        }
    }
}