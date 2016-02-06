package com.hilfritz.myappportfolio.ui.album.fullscreenphoto;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hilfritz.myappportfolio.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FullsizePhotoActivity extends Activity {
    @Bind(R.id.imageView)
    ImageView imageView;
    public static final String URL = "url";
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullsize_photo);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra("url");
        //url = AlbumActivity.url;
        if (!url.equalsIgnoreCase("") && url!=null) {
            Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.play)
                    .centerCrop()
                    .crossFade()
                    .into(imageView);
        }
    }

}
