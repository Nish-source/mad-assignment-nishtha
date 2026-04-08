package com.example.gallery;

import android.view.*;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.documentfile.provider.DocumentFile;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    interface OnClick {
        void onClick(DocumentFile file);
    }

    List<DocumentFile> list;
    OnClick listener;

    public ImageAdapter(List<DocumentFile> list, OnClick listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        DocumentFile file = list.get(pos);
        holder.image.setImageURI(file.getUri());

        holder.itemView.setOnClickListener(v -> listener.onClick(file));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.imageView);
        }
    }
}