package com.hilfritz.myappportfolio.ui.music;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hilfritz.myappportfolio.BaseFragment;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.spotsl.wrapper.Image;

import java.util.List;

/**
 * Created by Hilfritz P. Camallere on 6/16/2015.
 */
public class MusicPlayerAppUtil {
    public static final String TAG = "MusicPlayerAppUtil";

    /**
     *
     * @param imageView ImageView
     * @param imageList List<Image>
     * @param context Context
     * @param placeHolder int like R.drawable.sample
     * @return @return boolean TRUE if there is a URL for the imageview, FALSE otherwise
     */
    public static boolean loadImage(ImageView imageView, List<Image> imageList, Context context, int placeHolder){
        if (imageList!=null && imageList.size()>0) {
            //Image image = imageList.get(imageList.size()-1);
            Image image = getImageToDisplay(imageList);
            if (image!=null && image.getUrl().isEmpty() == false && image.getUrl().equalsIgnoreCase("")==false) {
                Glide.with(context)
                        .load(image.getUrl())
                        .placeholder(placeHolder)
                        .centerCrop()
                        .crossFade()
                        .into(imageView);
                //Log.d(TAG, "loading image width:"+image.getWidth()+" height:"+image.getHeight());
            }else{
                imageView.setImageDrawable(null);
            }
            return true;
        }else{
            //imageView.setImageDrawable(null);
        }
        return false;
    }

    public static void loadImage(Context context,ImageView imageView, String url, int placeHolder){
        if (url!=null && url.isEmpty() == false && url.equalsIgnoreCase("")==false) {
            Glide.with(context)
                    .load(url)
                    .placeholder(placeHolder)
                    .centerCrop()
                    .crossFade()
                    .into(imageView);
            //Log.d(TAG, "loading image width:"+image.getWidth()+" height:"+image.getHeight());
        }else{
            Log.d(TAG, "loadImage() error url");
        }
    }


    public static boolean isEven(int x){
        if (x % 2 == 0 ){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isEven(List<Image> imageList){
        if (imageList.size() % 2 == 0 ){
            return true;
        }else{
            return false;
        }
    }

    public static Image getImageToDisplay(List<Image> imageList){
        if (isEven(imageList)==false)
            imageList = makeImageListEven(imageList);
        imageList = getHalfOfList(imageList);
        return imageList.get(imageList.size()-1);
    }

    public static List<Image> makeImageListEven(List<Image> imageList){
        imageList.add(new Image());
        return imageList;
    }

    public static List<Image> getHalfOfList(List<Image> imageList){
        int totalListSize =imageList.size();
        int halfSize = totalListSize/2;
        for(int x=0; x!=halfSize; x++){
            imageList.remove(imageList.size()-1);
        }
        return imageList;
    }
}
