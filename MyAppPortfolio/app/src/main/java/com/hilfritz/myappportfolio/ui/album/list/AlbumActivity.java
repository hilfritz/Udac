package com.hilfritz.myappportfolio.ui.album.list;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hilfritz.myappportfolio.AppMainApplication;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.albumapi.AlbumApiManager;
import com.hilfritz.myappportfolio.albumapi.pojo.Album;
import com.hilfritz.myappportfolio.albumapi.pojo.Users;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AlbumActivity extends Activity {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.label2)
    TextView label;

    @Bind(R.id.edittext)
    EditText editText;

    AlbumListAdapter albumListAdapter;
    ArrayList<Album> albumList;

    @Inject
    AlbumApiManager albumApi;

    public static final String SHOW_LIST ="showList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        (((AppMainApplication) getApplication()).getRestApiComponent()).inject(this);
        albumList = new ArrayList<Album>();
        albumListAdapter = new AlbumListAdapter(this, albumList);
        listView.setAdapter(albumListAdapter);
        listView.setEmptyView(textView);
        observeErrorTextViewTextChange();
        loadAlbums();
        setErrorText("");
        addEditTextObserver();


    }

    private void addEditTextObserver(){

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateString(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Observable.just(validateString(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onNext(String s) {
                        validateString(s);
                    }
                });
    }

    private String validateString(String str){
        label.setText("VALIDATING:" + str);
        return str;
    }

    private void observeErrorTextViewTextChange(){
        String str = "";
        Observable.just(setErrorText(str))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onNext(String s) {
                       showDisplay(s);
                    }
                });
    }

    private void showDisplay(String s){
        if (s.equalsIgnoreCase(AlbumActivity.SHOW_LIST)){
            textView.setText("");
            textView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }else{
            textView.setText(s);
            textView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }


    private String setErrorText(String str){
        textView.setText(str);
        return str;
    }

    private void loadAlbums(){
        setErrorText("Loading albums...");
        albumApi.getAlbumsObservable().subscribe(new Subscriber<List<Album>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                showErrorLoadingList();
            }

            @Override
            public void onNext(List<Album> albums) {
                if (albums != null) {
                    albumList.clear();
                    albumList.addAll(albums);
                }
                getUserList();
            }
        });
        /*
        albumApi.getApi().getAlbumsO()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Album>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorLoadingList();
                    }

                    @Override
                    public void onNext(List<Album> albums) {
                        if (albums != null) {
                            albumList.clear();
                            albumList.addAll(albums);
                        }
                        getUserList();
                    }
                });
        */
    }

    private void getUserList(){
        setErrorText("Loading users...");
        albumApi.getUsersObservable().subscribe(new Subscriber<List<Users>>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {
                showErrorLoadingList();
            }
            @Override
            public void onNext(List<Users> userses) {
                showAlbumList(albumList, userses);
            }
        });
        /*
        albumApi.getApi().getallUserO()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Users>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        showErrorLoadingList();
                    }
                    @Override
                    public void onNext(List<Users> userses) {
                        showAlbumList(albumList, userses);
                    }
                });
                */
    }

    private void showAlbumList(List<Album> list, List<Users> usersList){
        if (list!=null && list.size()>0){
            albumListAdapter.getUserList().clear();
            albumListAdapter.setUserList(usersList);
            albumListAdapter.notifyDataSetChanged();
            setErrorText(AlbumActivity.SHOW_LIST);
        }else{
            showErrorLoadingList();
        }
    }

    private void showErrorLoadingList(){
        setErrorText("Error loading list of albums");
    }
}
