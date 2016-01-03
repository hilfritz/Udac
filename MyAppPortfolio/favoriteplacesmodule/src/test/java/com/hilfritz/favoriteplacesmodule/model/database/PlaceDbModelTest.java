package com.hilfritz.favoriteplacesmodule.model.database;

import android.os.Build;

import com.hilfritz.favoriteplacesmodule.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hilfritz P. Camallere on 1/2/2016.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class PlaceDbModelTest {

    @Before
    public void setup(){

    }



    @Test
    public void addPlaceTest(){
        List<PlaceDbModel> all = PlaceDbModel.getAllActive();
        assertEquals(0, all.size());

        PlaceDbModel p1 = new PlaceDbModel("Baclayan", "The province where I grew up");
        p1.save();

        all = PlaceDbModel.getAllActive();
        assertEquals(all.size(),1);
    }

    @Test
    public void getAllActiveTest(){
        PlaceDbModel pl1 = new PlaceDbModel("Baclayan", "The province where I grew up");
        pl1.status =  PlaceDbModel.STATUS_ACTIVE;
        pl1.save();

        PlaceDbModel pl2 = new PlaceDbModel("Cebu", "The city where I studied for college");
        pl2.status =  PlaceDbModel.STATUS_ACTIVE;
        pl2.save();

        PlaceDbModel pl3 = new PlaceDbModel("Dalaguete", "The prevince where I studied for high school");
        pl3.status =  PlaceDbModel.STATUS_DELETED;
        pl3.save();

        List<PlaceDbModel> all = PlaceDbModel.getAllActive();
        assertEquals(2, all.size());
    }

    //@Test
    public void getAllDeletedTest(){
        List<PlaceDbModel> all = PlaceDbModel.getAllDeleted();
        assertEquals(0, all.size());

        PlaceDbModel p1 = new PlaceDbModel("Baclayan1", "The province where I grew up");
        p1.status =  PlaceDbModel.STATUS_DELETED;
        p1.save();
        PlaceDbModel p2 = new PlaceDbModel("Cebu2", "The city where I studied for college");
        p2.status =  PlaceDbModel.STATUS_DELETED;
        p2.save();
        PlaceDbModel p3 = new PlaceDbModel("Dalaguete3", "The prevince where I studied for high school");
        p3.status =  PlaceDbModel.STATUS_DELETED;
        p3.save();

        List<PlaceDbModel> all2 = PlaceDbModel.getAllDeleted();
        assertEquals(1,all2.size());    //WRONG
    }


}
