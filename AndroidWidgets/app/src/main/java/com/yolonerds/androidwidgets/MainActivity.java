package com.yolonerds.androidwidgets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    Switch switch1;
    RadioButton radioButton1, radioButton2, radioButton3;
    TextView button1Text, radioText, seekBarText, buttonUpdateLabelText;
    CheckBox checkBox;
    EditText editText;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // declare widgets

        button1Text = (TextView)findViewById(R.id.button1text);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        radioText = (TextView)findViewById(R.id.radioText);
        seekBarText = (TextView)findViewById(R.id.sliderText);
        buttonUpdateLabelText = (TextView)findViewById(R.id.updateLabelText);
        editText = (EditText)findViewById(R.id.editText);

        // attach listeners

        switch1 = (Switch)findViewById(R.id.switch1);
        if (switch1 != null){
            switch1.setOnCheckedChangeListener(this);
        }

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        if (seekBar != null){
            seekBar.setOnSeekBarChangeListener(seekBarListener);
        }

        radioButton1 = (RadioButton)findViewById(R.id.radioButton);
        if (radioButton1 != null){
            radioButton1.setOnCheckedChangeListener(this);
        }

        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        if (radioButton2 != null){
            radioButton2.setOnCheckedChangeListener(this);
        }

        radioButton3 = (RadioButton)findViewById(R.id.radioButton3);
        if (radioButton3 != null){
            radioButton3.setOnCheckedChangeListener(this);
        }
    }

    // button1 callback (via XML onClick)

    public void button1Click(View view){
        button1Text.setText("Button 1 text");
    }

    // buttonUpdateLabel callback (via XML onClick)

    public void buttonUpdateLabelClick(View view){
        buttonUpdateLabelText.setText(editText.getText());
    }

    // checkBox callback (via XML onClick)

    public void checkBoxChecked(View view){
        if (checkBox.isChecked()){
            checkBox.setText("This checkbox is: checked");
        } else {
            checkBox.setText("This checkbox is: unchecked");
        }
    }

    // radio and switch callback

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        switch(buttonView.getId())
        {
            case R.id.radioButton:
                if (isChecked){
                    radioText.setText("Radio1 selected");
                }
                break;
            case R.id.radioButton2:
                if (isChecked){
                    radioText.setText("Radio2 selected");
                }
                break;
            case R.id.radioButton3:
                if (isChecked){
                    radioText.setText("Radio3 selected");
                }
                break;
            case R.id.switch1:
                if (isChecked){
                    switch1.setText("This switch is: on");
                } else {
                    switch1.setText("This switch is: off");
                }
                break;
        }
    }

    // seek bar listener

    public SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
            updateSeekBarText(progress);
        }

        public void onStartTrackingTouch(SeekBar seekBar){ }

        public void onStopTrackingTouch(SeekBar seekBar){ }
    };

    // change progress label

    public void updateSeekBarText(int progress){
        seekBarText.setText(progress + "%");
    }
}
