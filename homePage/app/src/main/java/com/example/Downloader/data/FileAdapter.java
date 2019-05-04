package com.example.Downloader.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Downloader.R;

import java.util.List;

public class FileAdapter extends ArrayAdapter<File> {

    private LayoutInflater inflater;
    private List<File> files;

    public FileAdapter(Context context, int resource, List<File> files) {
        super(context, resource, files);
        inflater = LayoutInflater.from(context);
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.size(); //returns total of items in the list
    }

    @Override
    public File getItem(int position) {
        return files.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.file_item_layout,
                    parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        File file = getItem(position);
        viewHolder.name.setText(file.getName()+"."+file.getExt());
        viewHolder.parts.setText(file.getParts());

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView parts;

        public ViewHolder(View view) {
            name = (TextView)view.findViewById(R.id.name);
            parts = (TextView) view.findViewById(R.id.parts);
        }
    }
}
