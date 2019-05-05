package com.example.Downloader.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Downloader.R;
import com.example.Downloader.data.File;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ItemHolder> {

    private List<File> files;
    private OnItemClickListener mItemClickListener;

    public FileAdapter(List<File> files, OnItemClickListener mItemClickListener) {
        this.files = files;
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file_item_layout, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        final File file = files.get(position);

        holder.name.setText(file.getName()+"."+file.getExt());
        holder.parts.setText(String.valueOf(file.getParts()));
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(position, file);
            }
        });
        holder.parts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(position, file);
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView parts;

        public ItemHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            parts = itemView.findViewById(R.id.parts);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, File file);
    }
}
