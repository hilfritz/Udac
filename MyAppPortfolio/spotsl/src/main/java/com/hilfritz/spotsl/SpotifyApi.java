package com.hilfritz.spotsl;

import com.hilfritz.spotsl.wrapper.SearchWrapper;
import com.hilfritz.spotsl.wrapper.TopTracksWrapper;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Hilfritz P. Camallere on 6/14/2015.
 */
public interface SpotifyApi {



    //@GET("/search?q={name}*&type=artist&limit="+DEFAULT_LIMIT)
    //public List<Artists> searchArtist(@Path("name") String name);

    //@GET("/search?q={name}*&type=artist&limit="+DEFAULT_LIMIT)
    @GET("/search")
     public SearchWrapper searchArtistW(
            @Query("q") String artistName,
            @Query("type") String type,         //should always be artist
            @Query("limit") int limit,
            @Query("offset") int offset);       //should either be DEFAULT_LIMIT + OFFSET

    @GET("/artists/{id}/top-tracks")
    public TopTracksWrapper getTopTracks(
            @Path("id") String id,
            @Query("country") String country,  //TODO - make this dynamic
            @Query("limit") int limit,
            @Query("offset") int offset
            );
}
