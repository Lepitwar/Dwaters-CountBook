/*
 *   OpenCounter
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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Creates OpenCounter
 * Allows direct updates an Opened Counter
 *
 * @author dwaters
 * @version 1.0
 * @see Counter
 * @see MainActivity
 * @since 1.0
 */
public class OpenCounter extends Activity{
    private EditText OpenComment;
    private EditText OpenName;
    private EditText OpenIvalue;
    private EditText OpenCvalue;
    private TextView OpenDate;
    private Integer Position;
    private  Date Dated;
    @Override
    /**
     * Creates the Activity when called from MainActivity
     */
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_counter);
        Button save = (Button) findViewById(R.id.OpenSave);
        //Defines all elements sent from MainActivity
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Integer ivalue = intent.getIntExtra("iValue", 0);
        Integer cvalue = intent.getIntExtra("cValue", 0);
        String comment = intent.getStringExtra("comment");
        Dated = (Date) intent.getSerializableExtra("date");
        //Defines and Defaults  Layout Elements
        Position = intent.getIntExtra("position", 0);
        System.out.println("Step 2: "+Position);

        OpenName = (EditText) findViewById((R.id.OpenName));
        OpenName.setText(name,TextView.BufferType.EDITABLE);

        OpenIvalue = (EditText) findViewById(R.id.OpenIvalue);
        OpenIvalue.setText(ivalue.toString(), TextView.BufferType.EDITABLE);

        OpenCvalue = (EditText) findViewById(R.id.OpenCvalue);
        OpenCvalue.setText(cvalue.toString(), TextView.BufferType.EDITABLE);

        OpenComment = (EditText) findViewById(R.id.OpenComment);
        OpenComment.setText(comment,TextView.BufferType.EDITABLE);

        OpenDate = (TextView) findViewById(R.id.OpenDate);
        String dated = new SimpleDateFormat("yyyy-MM-dd").format(Dated);
        OpenDate.setText("Last Modified: "+dated.toString());

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Gets all LayoutOut elements
                String name = OpenName.getText().toString();
                Integer iValue = Integer.parseInt( OpenIvalue.getText().toString() );
                Integer cValue = Integer.parseInt( OpenCvalue.getText().toString() );
                String comment = OpenComment.getText().toString();
                //Sends back data to MainActivity to have a Counter updated
                Intent intents = new Intent();
                intents.putExtra("name", name);
                intents.putExtra("iValue", iValue);
                intents.putExtra("cValue", cValue);
                intents.putExtra("comment", comment);
                intents.putExtra("date", Dated);
                intents.putExtra("position", Position);
                setResult(Activity.RESULT_OK, intents);
                finish();
            }

        });

    }



}
