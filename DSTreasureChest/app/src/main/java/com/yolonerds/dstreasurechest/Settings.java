// Copyright © 2016, YOLOnerds - Leszek Zychowski, All rights reserved

package com.yolonerds.dstreasurechest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    // declarations
    private SeekBar levelSeekbar;
    private TextView levelSeekbarText;
    private Switch DKQ_switch, AC_switch, RTV_switch, IC_switch, WOG_switch, TOH_switch, level_switch;

    // constant strings
    private static final String DKQ = "DKQ";
    private static final String AC = "AC";
    private static final String RTV = "RTV";
    private static final String IC = "IC";
    private static final String WOG = "WOG";
    private static final String TOH = "TOH";

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.bindUserInterface();
        this.setToolbar();

        this.restoreInstanceStateCustom();
    }

    // Android system back button listener
    @Override
    public void onBackPressed()
    {
        finish();
    }

    // ActionBar back button listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    // use singleton GeneratorOptions to restore the state as opposed to Android's native Bundle object
    // to prevent any data inconsistencies between GeneratorOptions and Bundle
    public void restoreInstanceStateCustom() {

        if (GeneratorOptions.getInstance().getLimitLevels()){
            level_switch.setChecked(true);
            levelSeekbar.setEnabled(true);
            levelSeekbar.setProgress(GeneratorOptions.getInstance().getLevels());
            levelSeekbarText.setText("Level " + GeneratorOptions.getInstance().getLevels());
        }

        DKQ_switch.setOnCheckedChangeListener(null);
        DKQ_switch.setChecked(GeneratorOptions.getInstance().getExpansions().contains(DKQ));
        DKQ_switch.setOnCheckedChangeListener(this);

        AC_switch.setOnCheckedChangeListener(null);
        AC_switch.setChecked(GeneratorOptions.getInstance().getExpansions().contains(AC));
        AC_switch.setOnCheckedChangeListener(this);

        RTV_switch.setOnCheckedChangeListener(null);
        RTV_switch.setChecked(GeneratorOptions.getInstance().getExpansions().contains(RTV));
        RTV_switch.setOnCheckedChangeListener(this);

        IC_switch.setOnCheckedChangeListener(null);
        IC_switch.setChecked(GeneratorOptions.getInstance().getExpansions().contains(IC));
        IC_switch.setOnCheckedChangeListener(this);

        WOG_switch.setOnCheckedChangeListener(null);
        WOG_switch.setChecked(GeneratorOptions.getInstance().getExpansions().contains(WOG));
        WOG_switch.setOnCheckedChangeListener(this);

        TOH_switch.setOnCheckedChangeListener(null);
        TOH_switch.setChecked(GeneratorOptions.getInstance().getExpansions().contains(TOH));
        TOH_switch.setOnCheckedChangeListener(this);
    }

    // ActionBar initializer
    private void setToolbar(){

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void bindUserInterface(){

        // expansion switches

        DKQ_switch = (Switch)findViewById(R.id.DKQ_switch);
        if (DKQ_switch != null){
            DKQ_switch.setOnCheckedChangeListener(this);
        }

        AC_switch = (Switch)findViewById(R.id.AC_switch);
        if (AC_switch != null){
            AC_switch.setOnCheckedChangeListener(this);
        }

        RTV_switch = (Switch)findViewById(R.id.RTV_switch);
        if (RTV_switch != null){
            RTV_switch.setOnCheckedChangeListener(this);
        }

        IC_switch = (Switch)findViewById(R.id.IC_switch);
        if (IC_switch != null){
            IC_switch.setOnCheckedChangeListener(this);
        }

        WOG_switch = (Switch)findViewById(R.id.WOG_switch);
        if (WOG_switch != null){
            WOG_switch.setOnCheckedChangeListener(this);
        }

        TOH_switch = (Switch)findViewById(R.id.TOH_switch);
        if (TOH_switch != null){
            TOH_switch.setOnCheckedChangeListener(this);
        }

        // level switch & seekbar

        level_switch = (Switch)findViewById(R.id.level_switch);
        if (level_switch != null){
            level_switch.setOnCheckedChangeListener(this);
        }

        levelSeekbarText = (TextView) findViewById(R.id.level_seekbar_text);

        levelSeekbar = (SeekBar) findViewById(R.id.leve_seekbar);
        if (levelSeekbar != null){
            levelSeekbar.setOnSeekBarChangeListener(levelSliderListener);
        }

        levelSeekbar.setEnabled(false);
    }

    public SeekBar.OnSeekBarChangeListener levelSliderListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int level, boolean fromUser){
            updateSeekBarText(level);
        }

        public void onStartTrackingTouch(SeekBar seekBar){ }

        public void onStopTrackingTouch(SeekBar seekBar){ }
    };

    public void updateSeekBarText(int level) {
        levelSeekbarText.setText("Level " + level);
        GeneratorOptions.getInstance().setLevels(level);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        switch(buttonView.getId())
        {
            case R.id.DKQ_switch:
                if (isChecked){
                    GeneratorOptions.getInstance().addExpansion(DKQ);
                } else {
                    GeneratorOptions.getInstance().removeExpansion(DKQ);
                }
                break;
            case R.id.AC_switch:
                if (isChecked){
                    GeneratorOptions.getInstance().addExpansion(AC);
                } else {
                    GeneratorOptions.getInstance().removeExpansion(AC);
                }
                break;
            case R.id.RTV_switch:
                if (isChecked){
                    GeneratorOptions.getInstance().addExpansion(RTV);
                } else {
                    GeneratorOptions.getInstance().removeExpansion(RTV);
                }
                break;
            case R.id.IC_switch:
                if (isChecked){
                    GeneratorOptions.getInstance().addExpansion(IC);
                } else {
                    GeneratorOptions.getInstance().removeExpansion(IC);
                }
                break;
            case R.id.WOG_switch:
                if (isChecked){
                    GeneratorOptions.getInstance().addExpansion(WOG);
                } else {
                    GeneratorOptions.getInstance().removeExpansion(WOG);
                }
                break;
            case R.id.TOH_switch:
                if (isChecked){
                    GeneratorOptions.getInstance().addExpansion(TOH);
                } else {
                    GeneratorOptions.getInstance().removeExpansion(TOH);
                }
                break;
            case R.id.level_switch:
                if (isChecked){
                    levelSeekbar.setEnabled(true);
                    GeneratorOptions.getInstance().setLimitLevels(true);
                } else {
                    levelSeekbar.setEnabled(false);
                    levelSeekbar.setProgress(0);
                    GeneratorOptions.getInstance().setLimitLevels(false);
                }
                break;
        }
    }
}
