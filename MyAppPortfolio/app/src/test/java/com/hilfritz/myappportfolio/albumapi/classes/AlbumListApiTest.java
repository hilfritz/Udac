package com.hilfritz.myappportfolio.albumapi.classes;

import android.os.Build;

import com.hilfritz.favoriteplacesmodule.BuildConfig;
import com.hilfritz.myappportfolio.albumapi.AlbumBaseRequest;
import com.hilfritz.myappportfolio.albumapi.pojo.Album;
import com.hilfritz.myappportfolio.albumapi.pojo.Photo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hilfritz P. Camallere on 2/3/2016.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class AlbumListApiTest {

    @Test
    public void getAlbumsTest(){
        List<Album> list = AlbumBaseRequest.getApi().getAlbums();
        assertTrue(list.size() > 0);
        List<Photo> photolist = AlbumBaseRequest.getApi().getAllPhoto();
        assertTrue(photolist.size() > 0);

        Photo photo = AlbumBaseRequest.getApi().getSinglePhoto("1");
        assertNotNull(photo);
        assertNotNull(photo.getAlbumId());
        assertNotNull(photo.getId());
        assertEquals(photo.getId().toString(), "1");
        assertNotNull(photo.getThumbnailUrl());
        assertNotNull(photo.getTitle());
    }
}
