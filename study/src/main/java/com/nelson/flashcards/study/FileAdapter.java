package com.nelson.flashcards.study;

import java.util.List;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Mike on 9/11/2014.
 */
public class FileAdapter extends ArrayAdapter<FileItem>{

    private Context context;
    private int id;
    private List<FileItem> items;

    public FileAdapter(Context context, int textViewResourceId, List<FileItem> objects){
        super(context, textViewResourceId, objects);
        this.context = context;
        id = textViewResourceId;
        items = objects;
    }

    public FileItem getFileItem(int id) {
        return items.get(id);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if(view == null){
            LayoutInflater v = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = v.inflate(id, null);
        }

        final FileItem o = items.get(position);

        if(o != null) {
            TextView textView = (TextView) view.findViewById(R.id.file_text_view);
            ImageView imageView = (ImageView) view.findViewById(R.id.file_icon);
            String uri = "drawable/" + o.getImage();
            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            Drawable image = context.getResources().getDrawable(imageResource);
            imageView.setImageDrawable(image);

            if(textView != null)
                textView.setText(o.getName());
        }

        return view;
    }


}
