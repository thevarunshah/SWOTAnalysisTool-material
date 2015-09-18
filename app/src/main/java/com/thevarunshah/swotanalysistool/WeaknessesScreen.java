package com.thevarunshah.swotanalysistool;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.thevarunshah.swotanalysistool.backend.Database;

public class WeaknessesScreen extends Fragment{


    String text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.weaknesses_screen, container, false);
        EditText et = (EditText) rootView.findViewById(R.id.specific_wkn);
        et.setText(text);
        et.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Database.getCurrentSWOT().setWeaknesses(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }

        });

        return rootView;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text =getArguments().getString("text");
    }

    public static WeaknessesScreen newInstance(String text) {
        WeaknessesScreen f = new WeaknessesScreen();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("text", text);
        f.setArguments(args);

        return f;
    }

}
