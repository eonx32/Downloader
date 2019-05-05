package com.example.Downloader.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Downloader.R;
import com.example.Downloader.data.FilePart;

import java.util.List;

public class FilePartAdapter extends RecyclerView.Adapter<FilePartAdapter.ItemHolder> {

    private List<FilePart> fileParts;
    private OnItemClickListener mItemClickListener;

    public FilePartAdapter(List<FilePart> fileParts, OnItemClickListener mItemClickListener) {
        this.fileParts = fileParts;
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_layout, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        final FilePart filePart = fileParts.get(position);

        holder.partName.setText("Part-"+filePart.getId());

        holder.partName.setOnClickListener(view -> mItemClickListener.onItemClick(position, filePart));
        holder.tick.setOnClickListener(view -> mItemClickListener.onItemClick(position, filePart));
        holder.layout1.setOnClickListener(view -> mItemClickListener.onItemClick(position, filePart));
        holder.layout2.setOnClickListener(view -> mItemClickListener.onItemClick(position, filePart));
    }

    @Override
    public int getItemCount() {
        return fileParts.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView partName;
        ImageView tick;
        RelativeLayout layout1;
        RelativeLayout layout2;

        public ItemHolder(View itemView) {
            super(itemView);
            partName = itemView.findViewById(R.id.part_name);
            tick = itemView.findViewById(R.id.tick);
            layout1 = itemView.findViewById(R.id.cardLayout1);
            layout2 = itemView.findViewById(R.id.cardLayout2);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, FilePart filePart);
    }
}
