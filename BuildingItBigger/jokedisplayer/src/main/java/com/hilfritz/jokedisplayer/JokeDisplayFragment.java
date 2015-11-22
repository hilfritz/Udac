package com.hilfritz.jokedisplayer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A placeholder fragment containing a simple view.
 */
public class JokeDisplayFragment extends Fragment {
    TextView textView;

    public JokeDisplayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textView = (TextView)view.findViewById(R.id.textView);
        return view;
    }

    public void showMessage(String str){
        textView.setText(str);
    }
}
