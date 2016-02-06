package com.hilfritz.myappportfolio.ui.album.photolist;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hilfritz.myappportfolio.AppMainApplication;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.albumapi.AlbumBaseRequest;
import com.hilfritz.myappportfolio.albumapi.pojo.Photo;
import com.hilfritz.myappportfolio.ui.album.fullscreenphoto.FullsizePhotoActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AlbumPhotoActivity extends Activity {
    @Bind(R.id.gridView)
    GridView gridView;

    @Bind(R.id.textView)
    TextView textView;

    ArrayList<Photo> list;
    AlbumPhotoListAdapter adapter;
    @Inject
    AlbumBaseRequest albumApi;

    public static final String ALBUM_ID = "albumid";
    public static final String ALBUM_OBJECT = "albumobject";
    int albumId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_photo);
        ButterKnife.bind(this);
        (((AppMainApplication) getApplication()).getRestApiComponent()).inject(this);
        list = new ArrayList<Photo>();
        adapter = new AlbumPhotoListAdapter(this, list);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //albumId = AlbumActivity.albumId;
        albumId = getIntent().getIntExtra("id", -1);

        //String str = getIntent().getStringExtra(AlbumPhotoActivity.ALBUM_OBJECT);
        //Album album = new Gson().fromJson(str,Album.class);
        loadPhotos(albumId);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Photo photo = (Photo)view.getTag(R.string.title_activity_album);
                Intent intent = new Intent(AlbumPhotoActivity.this, FullsizePhotoActivity.class);
                intent.putExtra("url", photo.getThumbnailUrl());
                //AlbumActivity.url = photo.getThumbnailUrl();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void loadPhotos(final int albumId){
        if (albumId!=-1){
            albumApi.getApi().getAllPhotoO()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Photo>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(AlbumPhotoActivity.this, "Error loading photos", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onNext(List<Photo> photos) {
                            if (photos != null) {

                                if (photos.size() > 0) {
                                    List<Photo> temp = new ArrayList<Photo>();
                                    int size=  photos.size();
                                    for (int x=0; x<size; x++){
                                        Photo photo = photos.get(x);
                                        if (photo.getAlbumId()==albumId){
                                            temp.add(photo);
                                        }
                                    }
                                    list.addAll(temp);
                                    adapter.notifyDataSetChanged();
                                    textView.setVisibility(View.GONE);
                                }
                            }
                        }
                    });
        }else{
            Toast.makeText(AlbumPhotoActivity.this, "Error loading photos.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
