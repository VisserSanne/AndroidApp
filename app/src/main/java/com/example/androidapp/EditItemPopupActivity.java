package com.example.androidapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.androidapp.Models.Vak;
import com.example.androidproject.R;
import com.google.android.material.textfield.TextInputEditText;

public class EditItemPopupActivity extends Activity {

    Vak currVak;
    Button submit;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle data = getIntent().getExtras();
        currVak = data.getParcelable("vak");

        setContentView(R.layout.activity_popup_edit_vak);

        TextView title = findViewById(R.id.title);
        title.setText("Verander " + currVak.getVakcode());

        TextInputEditText cijfer = findViewById(R.id.cijfer);
        if(currVak.getCijfer() != 0){
            cijfer.setText(Double.toString(currVak.getCijfer()));
        }
        cijfer.addTextChangedListener(getCijferTextWatcher());

        Switch heeftHerkansing = findViewById(R.id.herkansing);
        TextInputEditText herkansingCijfer = findViewById(R.id.herkansingCijfer);
        if(currVak.isHerkansing()){
            heeftHerkansing.setChecked(true);
            if(currVak.getH1cijfer() != 0){
                herkansingCijfer.setText(Double.toString(currVak.getH1cijfer()));
            }
        } else {
            herkansingCijfer.setVisibility(View.GONE);
        }
        herkansingCijfer.addTextChangedListener(getHerkansingCijferTextWatcher());
        heeftHerkansing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                currVak.setHerkansing(true);
                herkansingCijfer.setVisibility(View.VISIBLE);
            } else {
                currVak.setHerkansing(false);
            }
        });

        Switch volgend = findViewById(R.id.volgend);
        if(currVak.isVolgend()){
            volgend.setChecked(true);
        }
        volgend.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                currVak.setVolgend(true);
            } else {
                currVak.setVolgend(false);
            }
        });

        TextInputEditText notitie = findViewById(R.id.notitie);
        if(currVak.getNotitie() != null && currVak.getNotitie() != ""){
            notitie.setText(currVak.getNotitie());
        }
        notitie.addTextChangedListener(getNotitieTextWatcher());


        submit = findViewById(R.id.submit);
        Button cancel = findViewById(R.id.cancel);

        submit.setOnClickListener(v -> {
            submit();
        });
        cancel.setOnClickListener(v -> {
            cancel();
        });

    }

    public void submit(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", currVak);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void cancel(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    public TextWatcher getCijferTextWatcher() {
        TextWatcher textWatcher = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if(!text.toString().isEmpty()){
                    currVak.setCijfer(Double.parseDouble(text.toString()));
                    System.out.println(currVak.getCijfer());
                    if(currVak.getCijfer() >= 5.5 || currVak.getH1cijfer() >= 5.5){
                        currVak.setGehaald(true);
                    } else {
                        currVak.setGehaald(false);
                    }
                } else {
                    currVak.setCijfer(0);
                    currVak.setGehaald(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        return textWatcher;
    }

    public TextWatcher getHerkansingCijferTextWatcher(){
        TextWatcher textWatcher = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if(!text.toString().isEmpty()){
                    currVak.setH1cijfer(Double.parseDouble(text.toString()));
                    if(currVak.getCijfer() >= 5.5 || currVak.getH1cijfer() >= 5.5){
                        currVak.setGehaald(true);
                    } else {
                        currVak.setGehaald(false);
                    }
                } else {
                    currVak.setH1cijfer(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        return textWatcher;
    }

    public TextWatcher getNotitieTextWatcher(){
        TextWatcher textWatcher = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if(!text.toString().isEmpty()){
                    currVak.setNotitie(text.toString());
                } else {
                    currVak.setNotitie("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        return textWatcher;
    }

}
