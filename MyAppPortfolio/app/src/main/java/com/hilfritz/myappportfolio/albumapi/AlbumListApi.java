package com.hilfritz.myappportfolio.albumapi;

import com.hilfritz.myappportfolio.albumapi.pojo.Album;
import com.hilfritz.myappportfolio.albumapi.pojo.Photo;
import com.hilfritz.myappportfolio.albumapi.pojo.Users;

import java.net.URL;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Hilfritz P. Camallere on 2/3/2016.
 */
public interface AlbumListApi {
    public static final String URL="http://jsonplaceholder.typicode.com";

    @GET("/albums")
    public Observable<List<Album>> getAlbumsO();

    @GET("/photos")
    public Observable<List<Photo>> getAllPhotoO();

    @GET("/users/{id}")
    public Observable<Users> getSingleUserO(
            @Path("id") String id
    );

    @GET("/users")
    public Observable<List<Users>> getallUserO(
    );



    @GET("/albums")
    public List<Album> getAlbums();

    @GET("/photos")
    public List<Photo> getAllPhoto(
    );

    @GET("/photos/{id}")
    public Photo getSinglePhoto(
            @Path("id") String id
    );

    @GET("/users/{id}")
    public Users getSingleUser(
            @Path("id") String id
    );
}
