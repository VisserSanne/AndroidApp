package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.Database.DataHandler;
import com.example.androidapp.Models.Vak;
import com.example.androidapp.Recycler.RecyclerAdapter;
import com.example.androidapp.Recycler.RecyclerLinearLayoutManager;
import com.example.androidproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Courses extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton actionButton;

    ArrayList<Vak> allCourses = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View rootView = inflater.inflate(R.layout.fragment_courses, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new RecyclerLinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerAdapter(getData(),this, rootView));

        actionButton = rootView.findViewById(R.id.floatingActionButton);
        actionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateItemPopupActivity.class);
            getActivity().startActivityForResult(intent, 2);
        });

        return rootView;
    }

    public ArrayList<Vak> getData(){
        DataHandler dataHandler = DataHandler.getHandler();
        dataHandler.getClasses((data)->{
            allCourses.addAll(data);
            recyclerView.getAdapter().notifyDataSetChanged();
        }, DataHandler.Timespan.ALL);
        return allCourses;
    }

}
