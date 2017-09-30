/*
 *   MainActivity
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
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * Creates MainActivity
 *
 * @author dwaters
 * @version 1.0
 * @see Counter
 * @see DisplayAddCounter
 * @see OpenCounter
 * @since 1.0
 */
public class MainActivity extends Activity {
    private static final String FILENAME = "file.sav";
    private ListView CounterList;
    private ArrayList<Counter> counters = new ArrayList<Counter>();
    private ArrayAdapter<Counter> adapter;
    Button addButton;

    @Override
    /**
     * Creates the apps homepage for MainActivity
     * @see DisplayAddCounter
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Defines the Homepage Layout and related elements
        setContentView(R.layout.activity_main);
        addButton = (Button) findViewById(R.id.NewCounter);
        CounterList = (ListView) findViewById(R.id.CounterList);
        registerForContextMenu(CounterList);
        //When Add Button is clicked
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Launches DisplayAddCounter
                Intent intent =  new Intent(MainActivity.this, DisplayAddCounter.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    /**
     * Returns data from the other app pages
     * Either Creates a new Counter or Updates Existing Counter
     * @see DisplayAddCounter
     * @see OpenCounter
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // Returning from Adding a Counter (DisplayAddCounter)
        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){
                String name = data.getStringExtra("name");
                Integer value = data.getIntExtra("value", 0);
                String comment = data.getStringExtra("comment");
                Counter counter =new Counter(name, value, comment);
                counters.add(counter);
                adapter.notifyDataSetChanged();
                saveInFile();
            }
        } else if( requestCode==2){
            // Returning from Editing a Counter (OpenCounter)
            if(resultCode == Activity.RESULT_OK){
                String name = data.getStringExtra("name");
                Integer iValue = data.getIntExtra("iValue", 0);
                Integer cValue = data.getIntExtra("cValue", 0);
                String comment = data.getStringExtra("comment");
                Date date = (Date) data.getSerializableExtra("date");
                Integer position = data.getIntExtra("position", 0);

                Counter counter = counters.get(position);
                System.out.println("Step 4: "+position);
                counter.setName(name);
                counter.setInitialValue(iValue);
                counter.setCurrentValue(cValue);
                counter.setComments(comment);
                adapter.notifyDataSetChanged();
                saveInFile();
            }

        }
    }
    @Override
    /**
     * Define drop-down menu options when holding an Item from the List
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        if(v.getId()==R.id.CounterList){
            menu.add("Increase");
            menu.add("Decrease");
            menu.add("Open");
            menu.add("Reset");
            menu.add("Delete");
        }

    }

    /**
     * Define the actions of the drop-down menu options
     * @see Counter
     * @see OpenCounter
     */
    public boolean onContextItemSelected(MenuItem item){
        //Deleted the Selected Counter
        if( item.getTitle() =="Delete"){
            Toast.makeText(MainActivity.this, "Counter Deleted", Toast.LENGTH_SHORT).show();
            AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position= info.position;
            counters.remove(position);
            adapter.notifyDataSetChanged();
            saveInFile();
        //Increases Current Value of Selected Counter by 1
        }else if( item.getTitle() =="Increase") {
            Toast.makeText(MainActivity.this, "Counter Increased by 1", Toast.LENGTH_SHORT).show();
            AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position= info.position;
            Counter counter = counters.get(position);
            counter.CValueIncrease();
            adapter.notifyDataSetChanged();
            saveInFile();
        //Increases Current Value of Selected Counter by 1 (Can not go below 0)
        }else if( item.getTitle() =="Decrease") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;
            Counter counter = counters.get(position);
            if(counter.getCurrentValue()<=0){
                Toast.makeText(MainActivity.this, "Counter Can Not Go Below 0", Toast.LENGTH_SHORT).show();
            }else if (counter.getCurrentValue()>0) {
                counter.CValueDecrease();
                Toast.makeText(MainActivity.this, "Counter Decreased by 1", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
            saveInFile();
        //Opens the selected Counter
        }else if( item.getTitle() =="Open"){
            Toast.makeText(MainActivity.this, "Recorded Opened", Toast.LENGTH_SHORT).show();
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;
            Counter counter = counters.get(position);
            System.out.println("Step 1: "+position);
            String name = counter.getName();
            Integer iValue = counter.getInitialValue();
            Integer cValue = counter.getCurrentValue();
            String comment = counter.getComments();
            Date date = counter.getDate();

            Intent intent =  new Intent(MainActivity.this, OpenCounter.class);
            intent.putExtra("name", name);
            intent.putExtra("iValue", iValue);
            intent.putExtra("cValue", cValue);
            intent.putExtra("comment", comment);
            intent.putExtra("date", date);
            intent.putExtra("position", position);

            startActivityForResult(intent, 2);
        //Resets the Selected Counters curent Value to Initial
        }else if(item.getTitle() =="Reset"){
            Toast.makeText(MainActivity.this, "Counter Reset", Toast.LENGTH_SHORT).show();
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;
            Counter counter = counters.get(position);
            counter.resetCurrent();
            adapter.notifyDataSetChanged();
            saveInFile();
        }
        return true;
    }

    //Taken from LonelyTwitter
    @Override
    /**
     * Loads file and Sets CounterList
     */
    protected  void onStart(){
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Counter>(this,
                R.layout.list_item, counters);
        CounterList.setAdapter(adapter);
    }

    /**
     * Loads Counters from a saved file
     * Else creates a new Coutner Array
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counters = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            counters = new ArrayList<Counter>();
            //throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves Counters to file
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counters, writer);
            writer.flush();
            //fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

}
