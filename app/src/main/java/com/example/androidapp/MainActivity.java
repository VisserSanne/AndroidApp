package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidapp.Database.DataHandler;
import com.example.androidapp.Models.Vak;
import com.example.androidproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataHandler.getHandler(getApplicationContext());

        BottomNavigationView navigation_view = findViewById(R.id.navigation_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(navigation_view, navController);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode){
            case 2: this.handleCreatePopup(intent);
            case 3: this.handleEditPopup(intent);
        }
    }

    private void handleCreatePopup(Intent intent) {
        if(!(intent.getExtras() == null)){
            Bundle data = intent.getExtras();
            Vak newVak = (Vak) data.getParcelable("result");
            DataHandler datahander = DataHandler.getHandler();
            datahander.addClass(newVak.getJaar(), newVak.getPeriode(), newVak);
        }
    }
    private void handleEditPopup(Intent intent) {
        if (!(intent.getExtras() == null)) {
            Bundle data = intent.getExtras();
            Vak editedVak = (Vak) data.getParcelable("result");
            DataHandler datahander = DataHandler.getHandler();
            datahander.addClass(editedVak.getJaar(), editedVak.getPeriode(), editedVak);
        }
    }
}

