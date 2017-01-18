package com.hilfritz.bootstrap.util.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Hilfritz Camallere on 18/1/17.
 * For this to work your layout should have a layout that has
 * the attributes android:focusable="true" and android:focusable="true"
 * @See http://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 * @see
 */
public class EditTextInputAndSoftKeyboardHandler {
    private static final String TAG = "SoftKeyboardInputHandler";
    Activity activity;
    ArrayList<EditText> editTexts = new ArrayList<EditText>();
    View hiddenFocusableView = null;

    public interface SoftKeyboardStatusListener {
        void onKeyboardShow();
        void onKeyboardHide();
    }
    SoftKeyboardStatusListener softKeyboardStatusListener;

    public EditTextInputAndSoftKeyboardHandler(Activity activity, ArrayList<EditText> editTexts, View hiddenFocusableView, final SoftKeyboardStatusListener softKeyboardStatusListener) {
        Log.d(TAG, "SoftKeyboardInputHandler: ");
        this.activity = activity;
        this.editTexts = editTexts;
        this.hiddenFocusableView = hiddenFocusableView;
        addHideSoftKeyboardListener();
        this.softKeyboardStatusListener = softKeyboardStatusListener;
        if (editTexts!=null && editTexts.size()>0){
            int size = editTexts.size();
            for (int i = 0; i < size; i++) {
                EditText editText = editTexts.get(i);
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        Log.d(TAG, "onFocusChange: b:"+b+" on viewId:"+view.getId());
                        if (b){
                            softKeyboardStatusListener.onKeyboardShow();
                        }
                    }
                });
            }
        }
    }

    /**
     * Gets called whenever there is a click outside the edittexts
     */
    public void addHideSoftKeyboardListener(){
        this.activity.findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PUT THE FOCUS TO THE HIDDEN VIEW SO REMOVE THE FOCUS ON THE EDIT TEXTS
                hiddenFocusableView.requestFocus();
                hideSoftKeybaord();
                softKeyboardStatusListener.onKeyboardHide();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void hideSoftKeybaord(){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
