package com.hilfritz.myappportfolio.ui.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.albumapi.pojo.Album;
import com.hilfritz.myappportfolio.albumapi.pojo.Photo;
import com.hilfritz.myappportfolio.albumapi.pojo.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hilfritz P. Camallere on 2/3/2016.
 */
public class AlbumPhotoListAdapter extends ArrayAdapter<Photo> {
    Context context;

    public AlbumPhotoListAdapter(Context context, ArrayList<Photo> users) {
        super(context, 0, users);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Photo user = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_album_photo_list_item, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load(user.getThumbnailUrl())
                .placeholder(R.drawable.play)
                .centerCrop()
                .crossFade()
                .into(viewHolder.imageView);
        // Return the completed view to render on screen
        convertView.setTag(R.string.title_activity_album,  user);
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        ImageView imageView;
    }

}
