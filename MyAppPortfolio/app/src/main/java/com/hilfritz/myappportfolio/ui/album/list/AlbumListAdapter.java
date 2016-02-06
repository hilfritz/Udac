package com.hilfritz.myappportfolio.ui.album.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.albumapi.pojo.Album;
import com.hilfritz.myappportfolio.albumapi.pojo.Users;
import com.hilfritz.myappportfolio.ui.album.photolist.AlbumPhotoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hilfritz P. Camallere on 2/3/2016.
 */
public class AlbumListAdapter extends ArrayAdapter<Album> {

    List<Users> userList = null;
    Context context;

    public AlbumListAdapter(Context context, ArrayList<Album> users) {
        super(context, 0, users);
        userList = new ArrayList<Users>();
        this.context = context;
    }

    public List<Users> getUserList() {
        return userList;
    }

    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Album album = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_album_list_item, parent, false);
            viewHolder.albumTitle = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.user = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.albumTitle.setText("Album Title:" + album.getTitle());
        String name = getUserNameForId(album.getUserId());
        viewHolder.user.setText("Name: "+name);
        // Return the completed view to render on screen
        //convertView.setTag(R.string.title_activity_album,  user);
        ((RelativeLayout)viewHolder.albumTitle.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int albumId =album.getId();
                //Toast.makeText(context, "album name:" +AlbumActivity.albumId+" "+album.getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AlbumPhotoActivity.class);
                intent.putExtra("id", albumId);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView albumTitle;
        TextView user;
    }

    private String getUserNameForId(int id){
        String retVal = "";
        if (getUserList()!=null) {
            int size = getUserList().size();
            for (int x = 0; x < size; x++) {
                Users user = getUserList().get(x);
                if (user.getId() == id) {
                    return user.getName();
                }
            }
        }
        return retVal;
    }
}
