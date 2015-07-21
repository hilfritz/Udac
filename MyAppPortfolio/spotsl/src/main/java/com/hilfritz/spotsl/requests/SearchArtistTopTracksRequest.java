package com.hilfritz.spotsl.requests;

import com.hilfritz.spotsl.SpotifyApi;
import com.hilfritz.spotsl.wrapper.SearchWrapper;
import com.hilfritz.spotsl.wrapper.TopTracksWrapper;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by Hilfritz P. Camallere on 6/14/2015.
 */
public class SearchArtistTopTracksRequest extends RetrofitSpiceRequest<TopTracksWrapper, SpotifyApi> {
    public static final String COUNTRY = "PH";
    public static final int DEFAULT_LIMIT = 10;
    public static final int DEFAULT_OFFSET = 0;
    private String id;
    private int limit = DEFAULT_LIMIT;
    private int offset = DEFAULT_OFFSET;

    /**
     *
     * @param id String refers to the id of the artist
     * @param limit
     * @param offset
     */
    public SearchArtistTopTracksRequest(String id, int limit, int offset) {
        super(TopTracksWrapper.class, SpotifyApi.class);
        this.id = id;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public TopTracksWrapper loadDataFromNetwork() throws Exception {
        return getService().getTopTracks(id, COUNTRY, this.limit, this.offset);
    }
}
