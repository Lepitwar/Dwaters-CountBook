/*
 *   Counter
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

import android.renderscript.Sampler;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Creates the Counter Class
 * @author dwaters
 * @version 1.0
 * @see MainActivity
 * @see DisplayAddCounter
 * @see OpenCounter
 * @since 1.0
 */
public class Counter {
    private String Name;
    private Date Date;
    private Integer CurrentValue;
    private Integer InitialValue;
    private String Comments;

    /**
     * Constructs a Counter without a comment (not used)
     * @param Name Counter Name
     * @param InitialValue Counter's Initial Value (Sets Current Value)
     */
    public Counter(String Name, Integer InitialValue){
        Date = new Date();
        this.Name = Name;
        this.CurrentValue = InitialValue;
        this.InitialValue = InitialValue;
        this.Comments = "";
    }
    /**
     * Constructs a Counter without a comment, sets a new date
     * @param Name Counter Name
     * @param InitialValue Counter's Initial & Value Sets Current Value (Can not be below 0)
     * @param Comments  Counter's Optional Comments
     */
    public Counter(String Name, Integer InitialValue, String Comments){
        Date = new Date();
        this.Name = Name;
        if(InitialValue < 0 ){
            InitialValue=0;
        }
        this.CurrentValue = InitialValue;
        this.InitialValue = InitialValue;
        this.Comments = Comments;
    }

    /**
     * Allows the Counters name to be changed sets a new date
     * @param Name Counter's Name
     */
    public void setName(String Name){
        this.Name = Name;
        Date = new Date();
    }
    public String getName(){
        return this.Name;
    }

    /**
     * Allows the Counters Initial Value to be changes, sets a new date
     * @param InitialValue Counter's Initial Value
     */
    public void setInitialValue(Integer InitialValue){
        if(InitialValue < 0 ){
            InitialValue=0;
        }
        this.InitialValue = InitialValue;
        Date = new Date();
    }

    /**
     * Allows for direct editing of Current value, sets a new date
     * @param Value Counter's Current Value
     */
    public void setCurrentValue(Integer Value){
        if(Value < 0 ){
        Value=0;
        }

        this.CurrentValue = Value;
        Date = new Date();
    }

    /**
     * Returns the Counters Initial Value
     * @return InitialValue
     */
    public Integer getInitialValue() {
        return this.InitialValue;
    }

    /**
     * Returns the Counters Current Value
     * @return CurrentValue
     */
    public Integer getCurrentValue() {
        return this.CurrentValue;
    }

    /**
     * Returns Counter's Comments
     * @return Comments
     */
    public String getComments(){
        return Comments;
    }

    /**
     * Allows the edit of the Counter's Comments, sets a new date
     * @param comments
     */
    public void setComments(String comments){
        this.Comments= comments;
        Date = new Date();
    }

    /**
     * Returns the Counters Date
     * @return Date
     */
    public java.util.Date getDate() {
        return Date;
    }

    /**
     * Increments Current Value by 1, sets a new date
     */
    public void CValueIncrease(){
        CurrentValue++;
        Date = new Date();
    }

    /**
     *   Decreases Current Value by 1, sets a new date
     *   Can not go below 0
     */
    public void CValueDecrease(){
        Integer Value = this.getCurrentValue();
        if(Value < 0 ){
            InitialValue=0;
        }
        CurrentValue--;
        Date = new Date();
    }

    /**
     * Sets the Current Value to Initial Value
     */
    public void resetCurrent(){
        this.CurrentValue = this.InitialValue;
        Date = new Date();
    }
    @Override
    /**
     * Returns a String Representation of the Counter
     */
    public String toString(){
        String dated = new SimpleDateFormat("yyyy-MM-dd").format(this.Date);
        return( this.Name+": Current Value: "+CurrentValue+" \nLast Modified: "+dated);
    }

}
