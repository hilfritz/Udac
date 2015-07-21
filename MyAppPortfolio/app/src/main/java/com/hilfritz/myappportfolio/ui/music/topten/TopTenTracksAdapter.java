package com.hilfritz.myappportfolio.ui.music.topten;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hilfritz.myappportfolio.BaseActivity;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.ui.music.MusicPlayerAppUtil;
import com.hilfritz.myappportfolio.ui.music.player.MusicPlayerActivity;
import com.hilfritz.spotsl.wrapper.Image;
import com.hilfritz.spotsl.wrapper.Track;

import java.util.List;

/**
 * Created by Hilfritz P. Camallere on 6/16/2015.
 */
public class TopTenTracksAdapter extends RecyclerView.Adapter<TopTenTracksAdapter.TopTrackListItemViewHolder>{
    List<Track> trackList;
    private static final String TAG = "SearchArtistAdapter";
    TopTenTracksFragment topTenTracksFragment;
    TopTrackListItemViewHolder.ListItemClickListener listItemClickListener;

    public TopTenTracksAdapter(List<Track> trackList, TopTenTracksFragment topTenTracksFragment) {
        this.trackList = trackList;
        this.topTenTracksFragment = topTenTracksFragment;
    }

    public void setListItemClickListener(TopTrackListItemViewHolder.ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    @Override
    public TopTrackListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_track, parent, false);
        TopTrackListItemViewHolder topTrackListItemViewHolder = new TopTrackListItemViewHolder(view, topTenTracksFragment);
        return topTrackListItemViewHolder;
    }

    @Override
    public void onBindViewHolder(TopTrackListItemViewHolder holder, int position) {
        holder.trackName.setText(trackList.get(position).getName());
        holder.albumName.setText(trackList.get(position).getAlbum().getName());
        List<Image> imageList = trackList.get(position).getAlbum().getImages();
        MusicPlayerAppUtil.loadImage(holder.imageView, imageList, topTenTracksFragment.getActivity(), R.drawable.music_album);

        if (MusicPlayerAppUtil.isEven(position)){
            holder.relativeLayout.setBackgroundResource(R.drawable.item_listview_odd_selector);
        }else{
            holder.relativeLayout.setBackgroundResource(R.drawable.item_listview_even2_selector);
        }

        holder.relativeLayout.setTag(R.string.top_ten_tracks, trackList.get(position));
        holder.relativeLayout.setTag(R.string.index, position);
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public static class TopTrackListItemViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        private static final String TAG = "TopTrackListItemViewHolder";
        RelativeLayout relativeLayout;
        ImageView imageView;
        TextView trackName;
        ListItemClickListener mlistItemClickListener;


        TextView albumName;
        public TopTrackListItemViewHolder(View itemView, ListItemClickListener listItemClickListener) {
            super(itemView);
            mlistItemClickListener = listItemClickListener;
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            trackName = (TextView)itemView.findViewById(R.id.trackNameTextView);
            albumName = (TextView)itemView.findViewById(R.id.albumNameTextView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
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
