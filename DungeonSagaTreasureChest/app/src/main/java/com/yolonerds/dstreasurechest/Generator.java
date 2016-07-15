// Copyright © 2016, YOLOnerds - Leszek Zychowski, All rights reserved

package com.yolonerds.dstreasurechest;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class Generator extends AppCompatActivity {

    // declarations
    private DataBaseHelper myDbHelper;
    private ArrayList<Item> items = new ArrayList<>();
    private TextView itemDisplayText, levelDisplayText, typeDisplayText, messageDisplayText, expansionDisplayText;
    private Button generateButton;
    private Item currentItem;

    // constant strings
    public static final String WELCOME_MESSAGE = "Welcome to the Dungeon Saga Random Treasure Generator\n\n"
                                                + "1. Click on the SETTINGS button and select at least one expansion you would like to include.\n\n"
                                                + "2. Navigate back to the main screen and click on the FIND RANDOM TREASURE button.\n\n"
                                                + "3. Enjoy your random treasure!";

    private static final String DKQ = "DKQ";
    private static final String AC = "AC";
    private static final String RTV = "RTV";
    private static final String IC = "IC";
    private static final String WOG = "WOG";
    private static final String TOH = "TOH";

    private static final String DKQ_NAME = "Dwarf King's Quest";
    private static final String AC_NAME = "Adventurer's Companion";
    private static final String RTV_NAME = "Return to Valandor";
    private static final String IC_NAME = "Infernal Crypts";
    private static final String WOG_NAME = "Warlord of Galahir";
    private static final String TOH_NAME = "Tyrant of Halpi";

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        itemDisplayText = (TextView)findViewById(R.id.item_display_text);
        levelDisplayText = (TextView)findViewById(R.id.level_display_text);
        typeDisplayText = (TextView)findViewById(R.id.type_display_text);
        messageDisplayText = (TextView)findViewById(R.id.message_display_text);
        expansionDisplayText = (TextView)findViewById(R.id.expansion_display_text);

        generateButton = (Button)findViewById(R.id.generate_button);

        itemDisplayText.setText(WELCOME_MESSAGE);

        setToolbar();
        resetUi();
        createDataBase();
        openDataBase();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        resetUi();
    }

    public void resetUi(){

        itemDisplayText.setText("");
        levelDisplayText.setText("");
        typeDisplayText.setText("");
        expansionDisplayText.setText("");

        System.out.println("SIZE: " + GeneratorOptions.getInstance().getExpansions().size());

        if(GeneratorOptions.getInstance().getExpansions().size() != 0){
            generateButton.setEnabled(true);
            if (currentItem != null){
                displayItem(currentItem);
            } else {
                messageDisplayText.setText(WELCOME_MESSAGE);
            }
        } else {
            generateButton.setEnabled(false);
            messageDisplayText.setText(WELCOME_MESSAGE);
            Toast.makeText(getApplicationContext(), "No expansion selected", Toast.LENGTH_LONG).show();
        }
    }

    public void openSettings(View view){

        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    // ActionBar initializer
    private void setToolbar(){

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
    }

    private String getExpansionName(String expansionCode){
        String expansionName = "";
        switch(expansionCode){
            case DKQ:
                expansionName = DKQ_NAME;
                break;
            case AC:
                expansionName = AC_NAME;
                break;
            case RTV:
                expansionName = RTV_NAME;
                break;
            case IC:
                expansionName = IC_NAME;
                break;
            case WOG:
                expansionName = WOG_NAME;
                break;
            case TOH:
                expansionName = TOH_NAME;
                break;
        }
        return expansionName;
    }

    private void createDataBase(){

        myDbHelper = new DataBaseHelper(this);

        try {
            myDbHelper.createDataBase();
        } catch (IOException ex) {
            throw new Error("Unable to create database");
        }
    }

    private void openDataBase(){

        try {
            myDbHelper.openDataBase();
        } catch(SQLException ex){
            throw ex;
        }
    }

    public void displayItem(View view){

        Item item = getItem();
        currentItem = item;
        displayItem(item);
    }

    public void displayItem(Item item){

        itemDisplayText.setText(item.itemname);
        levelDisplayText.setText(String.valueOf("Required Level " + item.itemlevel));
        typeDisplayText.setText(item.itemtype);
        expansionDisplayText.setText(getExpansionName(item.itemset));
        messageDisplayText.setText("");
    }

    public Item getItem(){

        getList();
        int random = (int)(Math.random() * items.size() - 1);
        return items.get(random);
    }

    private void getList(){

        items.clear();
        Item item;

        try {
            Cursor cursor = myDbHelper.getList();

            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        item = new Item();
                        item._id = cursor.getInt(cursor.getColumnIndex("_id"));
                        item.itemname = cursor.getString(cursor.getColumnIndex("itemname"));
                        item.itemset = cursor.getString(cursor.getColumnIndex("itemset"));
                        item.itemtype = cursor.getString(cursor.getColumnIndex("itemtype"));
                        item.itemlevel = cursor.getInt(cursor.getColumnIndex("itemlevel"));
                        item.itemvalue = cursor.getInt(cursor.getColumnIndex("itemvalue"));

                        items.add(item);
                    } while (cursor.moveToNext());
                }
            }
        } catch(SQLException ex){
            throw ex;
        }
    }
}
