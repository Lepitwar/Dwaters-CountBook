/*
 *   DisplayAddCounter
 *
 *   Version 1.0
 *
 *   Sept. 29 2017
 *
 *   Copyright (c) 2017. Team X CMPUT301, Univeristy of Alberta - All Rights Reserved
 *   You may use, distribute or modify this code under terms and conditions of the Code of Student
 *   Behaviour at Univeristy of Alberta.  You can find a copy of the license in this project.
 *   Otherwise please contact dwaters@ualberta.ca
 */
package com.example.dwaters.dwaters_countbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Creates DisplayAddCounter, gathers data for a new Coutner
 * @author dwaters
 * @version 1.0
 * @see MainActivity
 * @see Counter
 * @since 1.0
 */
public class DisplayAddCounter extends Activity {

    private EditText CounterName;
    private EditText Comments;
    private EditText Value;

    @Override
    /**
     * Creates the Activity when called from MainActivity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Defines Layout Elements
        setContentView(R.layout.activity_display_add_counter);

        Button addNew = (Button) findViewById(R.id.AddNewCounter);
        CounterName = (EditText) findViewById(R.id.OpenName);
        Comments = (EditText) findViewById(R.id.OpenComment);
        Value = (EditText) findViewById(R.id.OpenIvalue);


        addNew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //returns Enetered Data to MainActivity to have a new Counter to be created.
                String name = CounterName.getText().toString();
                String comment = Comments.getText().toString();
                Integer value = Integer.parseInt( Value.getText().toString() );
                Intent intent = new Intent();
                intent.putExtra("name", name );
                intent.putExtra("value", value);
                intent.putExtra("comment", comment);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
    }


}
