package com.example.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.androidapp.Models.Vak;
import com.example.androidproject.R;
import com.google.android.material.textfield.TextInputEditText;

public class CreateItemPopupActivity extends Activity {

    Vak newVak;
    Button submit;
    boolean isKeuzevak = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_create_vak);

        newVak = new Vak();

        TextInputEditText vakcode = findViewById(R.id.vakCode);
        vakcode.addTextChangedListener(getVakcodeTextWatcher());

        TextInputEditText ec = findViewById(R.id.ec);
        ec.addTextChangedListener(getEcTextWatcher());

        submit = findViewById(R.id.submit);
        submit.setEnabled(false);
        Button cancel = findViewById(R.id.cancel);

        submit.setOnClickListener(v -> {
            submit();
        });
        cancel.setOnClickListener(v -> {
            cancel();
        });

    }

    public void checkKeuzevak(View view){
        if(isKeuzevak){
            isKeuzevak = false;
        } else {
            isKeuzevak = true;
        }
    }

    public void onYearRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()){
            case R.id.year1:
                newVak.setJaar("Studiejaar 1");
                break;
            case R.id.year2:
                newVak.setJaar("Studiejaar 2");
                break;
            case R.id.year3:
                newVak.setJaar("Studiejaar 3");
                break;
            case R.id.year4:
                newVak.setJaar("Studiejaar 4");
                break;
        }
        isFormValid();
    }
    public void onPeriodRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()){
            case R.id.period1:
                newVak.setPeriode("Periode 1");
                break;
            case R.id.period2:
                newVak.setPeriode("Periode 2");
                break;
            case R.id.period3:
                newVak.setPeriode("Periode 3");
                break;
            case R.id.period4:
                newVak.setPeriode("Periode 4");
                break;
        }
        isFormValid();
    }

    public void submit(){
        newVak.setKeuzevak(isKeuzevak);
        newVak.setCijfer(0);
        newVak.setGehaald(false);
        newVak.setHerkansing(false);
        newVak.setH1cijfer(0);
        newVak.setNotitie("");
        newVak.setVolgend(false);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", newVak);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void cancel(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    public TextWatcher getVakcodeTextWatcher() {
        TextWatcher textWatcher = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if(text != ""){
                    newVak.setVakcode(text.toString());
                } else {
                    newVak.setVakcode("");
                }
                isFormValid();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        return textWatcher;
    }

    public TextWatcher getEcTextWatcher() {
        TextWatcher textWatcher = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if(!text.toString().isEmpty()){
                    newVak.setEc(Integer.parseInt(text.toString()));
                } else {
                    newVak.setEc(0);
                }
                isFormValid();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        return textWatcher;
    }

    private boolean isFormValid(){
        if(newVak.getVakcode() != null && !newVak.getVakcode().isEmpty() ){
            if(newVak.getEcInteger() != null && newVak.getEc() != 0){
                if(newVak.getJaar() != null){
                    if(newVak.getPeriode() != null){
                        submit.setEnabled(true);
                        return true;
                    }
                }
            }
        }
        submit.setEnabled(false);
        return false;
    }

}
