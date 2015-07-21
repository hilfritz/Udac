package it.jaschke.alexandria.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Hilfritz P. Camallere on 6/27/2015.
 */
public class MyToast {
    //Declare as class variable
    private static Toast mAppToast = null;

    public static void showShortToast(Context context,String str) {

        //Stop any previous toasts
        if(mAppToast!=null){
            mAppToast.cancel();
        }
        //Make and display new toast
        mAppToast=Toast.makeText(context,str,Toast.LENGTH_SHORT);
        mAppToast.show();
    }

    public static void showLongToast(Context context,String str) {

        //Stop any previous toasts
        if(mAppToast!=null){
            mAppToast.cancel();
        }
        //Make and display new toast
        mAppToast=Toast.makeText(context,str,Toast.LENGTH_LONG);
        mAppToast.show();
    }

}
