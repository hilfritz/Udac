package com.hilfritz.spotsl;



import com.hilfritz.spotsl.wrapper.SearchWrapper;
import com.hilfritz.spotsl.wrapper.TopTracksWrapper;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Hilfritz P. Camallere on 6/14/2015.
 */
public interface SpotifyApi {
    public static final String SPOTIFY_ENDPOINT = "https://api.spotify.com/v1";

    @GET("/search")
     public SearchWrapper searchArtistW(
            @Query("q") String artistName,
            @Query("type") String type,         //should always be artist
            @Query("limit") int limit,
            @Query("offset") int offset);       //should either be DEFAULT_LIMIT + OFFSET

    @GET("/search")
    public Observable<SearchWrapper> searchArtistObservable(
            @Query("q") String artistName,
            @Query("type") String type,         //should always be artist
            @Query("limit") int limit,
            @Query("offset") int offset);

    @GET("/artists/{id}/top-tracks")
    public TopTracksWrapper getTopTracks(
            @Path("id") String id,
            @Query("country") String country,  //TODO - make this dynamic
            @Query("limit") int limit,
            @Query("offset") int offset
            );

    @GET("/artists/{id}/top-tracks")
    public Observable <TopTracksWrapper> getTopTracksObservable(
            @Path("id") String id,
            @Query("country") String country,  //TODO - make this dynamic
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
