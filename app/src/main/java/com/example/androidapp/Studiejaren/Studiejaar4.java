package com.example.androidapp.Studiejaren;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Database.DataHandler;
import com.example.androidapp.Models.Vak;
import com.example.androidapp.Recycler.RecyclerAdapter;
import com.example.androidapp.Recycler.RecyclerLinearLayoutManager;
import com.example.androidproject.R;

import java.util.ArrayList;

public class Studiejaar4 extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Vak> allCourses = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_studiejaar4, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new RecyclerLinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerAdapter(getData(),this, rootView));
        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        view.findViewById((R.id.back)).setOnClickListener(view1 ->
                NavHostFragment.findNavController(Studiejaar4.this)
                        .navigate(R.id.action_studiejaar4_to_studiejaarFragment));
    }

    public ArrayList<Vak> getData(){
        DataHandler dataHandler = DataHandler.getHandler();
        dataHandler.getClasses((data)->{
            allCourses.addAll(data);
            recyclerView.getAdapter().notifyDataSetChanged();
        }, DataHandler.Timespan.YEAR, 4);
        return allCourses;
    }
}
