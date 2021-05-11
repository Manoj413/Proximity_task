package com.proximity.myapplication.detail;

import com.proximity.myapplication.home.model.AqiData;

import java.util.ArrayList;
import java.util.List;

public class Holder
{
    private static Holder instance = null;
    private List<AqiData> itemArray;
    private Holder(){
        itemArray = new ArrayList<>();
    }

    public static Holder getInstance(){
        if (instance == null)
            instance = new Holder();
        return instance;
    }

    public ArrayList<AqiData> getItemArray(){
        return new ArrayList<>(this.itemArray);
    }

    public void addItemToArray(AqiData item){
        this.itemArray.add(item);
    }
    public void clearItemArray(){
        this.itemArray.clear();
    }
}