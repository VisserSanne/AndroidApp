package com.example.androidapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidapp.Database.DataHandler;
import com.example.androidapp.Models.Vak;
import com.example.androidproject.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import static android.graphics.Color.parseColor;

public class Homepage extends Fragment {

    PieChartView pieChartView;

    int totalEc = 0;
    int totalEcBehaald = 0;

    int totalEcNietBehaaldProcent = 0;
    int totalEcBehaaldProcent = 0;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChartView = view.findViewById(R.id.chart);
        pieChartView.setVisibility(View.INVISIBLE);

        getECData();


    }

    private void filterData(List<Vak> vakken){
//        totalEc = 0;
//        totalEcBehaald = 0;

        for(Vak vak: vakken){
            totalEc += vak.getEc();
            if(vak.isGehaald()){
                totalEcBehaald += vak.getEc();
            }
        }
        totalEcBehaaldProcent = Math.round(totalEcBehaald/(float)totalEc * 100f);
        totalEcNietBehaaldProcent = 100 - totalEcBehaaldProcent;
    }

    private void getECData(){
        DataHandler dataHandler = DataHandler.getHandler();
        dataHandler.getClasses((data)->{
            List<SliceValue> pieData;
            pieData = new ArrayList<>();
            System.out.println(data);
            filterData(data);
            pieData.add(new SliceValue(totalEcBehaaldProcent, parseColor("#03DAC5")).setLabel("EC behaald"));
            pieData.add(new SliceValue(totalEcNietBehaaldProcent, parseColor("#00baa8")).setLabel("EC te behalen"));
            pieChartView.setVisibility(View.VISIBLE);
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartView.setPieChartData(pieChartData);
        }, DataHandler.Timespan.ALL);
    }

}
