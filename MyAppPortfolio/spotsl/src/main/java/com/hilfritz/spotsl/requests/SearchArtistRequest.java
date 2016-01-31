package com.hilfritz.spotsl.requests;

import com.hilfritz.spotsl.SpotifyApi;
import com.hilfritz.spotsl.wrapper.SearchWrapper;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by Hilfritz P. Camallere on 6/14/2015.
 */
public class SearchArtistRequest extends RetrofitSpiceRequest<SearchWrapper, SpotifyApi> {
    public static final String TYPE_ARTIST = "artist";
    public static final int DEFAULT_LIMIT = 5;
    public static final int DEFAULT_OFFSET = 0;
    private String artistName;
    private int limit = DEFAULT_LIMIT;
    private int offset = DEFAULT_OFFSET;

    public SearchArtistRequest(String artistName, int limit, int offset) {
        super(SearchWrapper.class, SpotifyApi.class);
        this.artistName = artistName;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public SearchWrapper loadDataFromNetwork() throws Exception {
        return getService().searchArtistW(artistName, TYPE_ARTIST, this.limit, this.offset);
    }

}
