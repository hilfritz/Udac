package com.hilfritz.myappportfolio.ui.music.search;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.ui.music.MusicPlayerAppUtil;
import com.hilfritz.myappportfolio.ui.music.topten.TopTenTracksActivity;
import com.hilfritz.spotsl.wrapper.Image;
import com.hilfritz.spotsl.wrapper.Item;
import com.pkmmte.view.CircularImageView;

import java.util.List;

/**
 * Created by Hilfritz P. Camallere on 6/14/2015.
 * @see RecyclerView item onClick implementation - http://stackoverflow.com/questions/24885223/why-doesnt-recyclerview-have-onitemclicklistener-and-how-recyclerview-is-dif
 * http://stackoverflow.com/questions/24471109/recyclerview-onclick
 */
public class SearchArtistAdapter extends RecyclerView.Adapter<SearchArtistAdapter.ListItemViewHolder> {
    private List<Item> artistList;
    private static final String TAG = "SearchArtistAdapter";
    private SearchArtistFragment searchArtistFragment;
    ListItemViewHolder.ListItemClickListener listItemClickListener;

    int activeIndex = -1;

    public SearchArtistAdapter(List<Item> artistList, SearchArtistFragment searchArtistFragment) {
        this.artistList = artistList;
        this.searchArtistFragment = searchArtistFragment;
    }

    public void setListItemClickListener(ListItemViewHolder.ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public List<Item> getArtistList() {
        return artistList;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_artist, viewGroup, false);
        ListItemViewHolder listItemViewHolder = new ListItemViewHolder(i, view, listItemClickListener);
        return listItemViewHolder;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder listItemViewHolder, int position) {
        listItemViewHolder.textView.setText(artistList.get(position).getName());
        listItemViewHolder.textView.setTag(R.string.search_activity, artistList.get(position));
        listItemViewHolder.textView.setTag(R.string.index, position);
        List<Image> imageList = artistList.get(position).getImages();
        boolean imageLoaded = MusicPlayerAppUtil.loadImage(listItemViewHolder.imageView,imageList,searchArtistFragment.getActivity(), R.drawable.music_artist);
        if (position == activeIndex){
            listItemViewHolder.relativeLayout.setBackgroundResource(R.drawable.item_listview_active);
        }else{
            listItemViewHolder.relativeLayout.setBackgroundResource(R.drawable.item_listview_odd_selector);
        }
    }


    @Override
    public int getItemCount() {
        return artistList.size();
    }



    public static class ListItemViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        RelativeLayout relativeLayout;
        ImageView imageView;
        //CircularImageView imageView;
        ImageView imageViewBackgroundA;
        ListItemClickListener mlistItemClickListener;
        int position;

        TextView textView;
        public ListItemViewHolder(int position, View itemView, ListItemClickListener listItemClickListener) {
            super(itemView);
            this.position = position;
            mlistItemClickListener = listItemClickListener;
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            textView = (TextView)itemView.findViewById(R.id.textView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            imageViewBackgroundA = (ImageView)itemView.findViewById(R.id.imageViewBackgroundA);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mlistItemClickListener.handleClick(view);
        }

        public interface ListItemClickListener{
            void handleClick(View view);
        }

    }
}
