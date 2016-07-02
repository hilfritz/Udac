package com.hilfritz.bootstrap.view.main.userlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.api.pojo.UserWrapper;

import java.util.List;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 */

public class UserListAdapter extends ArrayAdapter<UserWrapper> {
    List<UserWrapper> items;
    Context context;

    public UserListAdapter(Context context, List<UserWrapper> list) {
        super(context, R.layout.user_list_item);
        items = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_list_item, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.email = (TextView) convertView.findViewById(R.id.email);

            // store the holder with the view.
            convertView.setTag(viewHolder);
        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UserWrapper user = items.get(position);
        if (user!=null){
            viewHolder.name.setText(user.getName());
            viewHolder.name.setTag(R.string.view_tag1, user);
            viewHolder.email.setText(user.getEmail());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView email;
        int position;

    }

    @Override
    public int getCount() {
        return items.size();
    }

}
