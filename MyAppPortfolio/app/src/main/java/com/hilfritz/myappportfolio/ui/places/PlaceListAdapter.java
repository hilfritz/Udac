package com.hilfritz.myappportfolio.ui.places;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hilfritz.favoriteplacesmodule.model.database.PlaceDbModel;
import com.hilfritz.myappportfolio.R;

/**
 * Created by Hilfritz P. Camallere on 1/2/2016.
 */
public class PlaceListAdapter extends CursorAdapter {
    public PlaceListAdapter(Context context, Cursor c) {
        super(context, c);
    }
    public PlaceListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_placelist_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String name = cursor.getString(cursor.getColumnIndexOrThrow(PlaceDbModel.COLUMN_NAME));
        viewHolder.name.setText(name);

        String description = cursor.getString(cursor.getColumnIndexOrThrow(PlaceDbModel.COLUMN_DESCRIPTION));
        viewHolder.description.setText(description);
    }

    public static class ViewHolder {
        public final ImageView image;
        public final TextView name;
        public final TextView description;

        public ViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.imageView);
            name = (TextView) view.findViewById(R.id.nameTextView);
            description = (TextView) view.findViewById(R.id.descriptionTextView);
        }
    }


}
