package com.hilfritz.myappportfolio.ui.places;

import com.activeandroid.ActiveAndroid;
import com.hilfritz.favoriteplacesmodule.model.database.PlaceDbModel;
import com.hilfritz.myappportfolio.BaseActivity;
import com.hilfritz.myappportfolio.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class PlaceActivity extends BaseActivity {

    @Override
    public void init() {
        setContentView(R.layout.activity_place);
        ButterKnife.bind(this);
        prepopulatePlaces();

        int id = R.id.container;
        /*
        if(findViewById(R.id.right_container) != null){
            id = R.id.right_container;
        }*/
        getSupportFragmentManager().beginTransaction()
                .replace(id, new PlaceListFragment())
                .addToBackStack("Place List Fragment")
                .commit();

    }

    public boolean prepopulatePlaces(){
        boolean retVal = false;
        PlaceDbModel p1 = new PlaceDbModel("Baclayan", "The Place where I grew up");
        PlaceDbModel p2 = new PlaceDbModel("Dalaguet", "The Place where I Studied high school");
        PlaceDbModel p3 = new PlaceDbModel("Cebu City", "The Place where I went for college");
        PlaceDbModel p4 = new PlaceDbModel("Alegria", "some place in southern part of cebu");
        PlaceDbModel p5 = new PlaceDbModel("Ronda", "The Place of my dad");
        List<PlaceDbModel> list = new ArrayList<PlaceDbModel>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i< list.size(); i++) {
                list.get(i).save();
            }
            ActiveAndroid.setTransactionSuccessful();
            retVal = true;
        }
        finally {
            ActiveAndroid.endTransaction();
        }
        return retVal;
    }


    @Override
    public void afterInitViews() {

    }
}
