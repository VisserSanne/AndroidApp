package com.example.androidapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidapp.Studiejaren.Studiejaar1;
import com.example.androidproject.R;

public class Studiejaar extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_studiejaar, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        view.findViewById((R.id.jaar1)).setOnClickListener(v ->
                NavHostFragment.findNavController(Studiejaar.this)
                .navigate(R.id.action_studiejaarFragment_to_studiejaar1));

        view.findViewById((R.id.jaar2)).setOnClickListener(v ->
                NavHostFragment.findNavController(Studiejaar.this)
                        .navigate(R.id.action_studiejaarFragment_to_studiejaar2));

        view.findViewById((R.id.jaar3)).setOnClickListener(v ->
                NavHostFragment.findNavController(Studiejaar.this)
                        .navigate(R.id.action_studiejaarFragment_to_studiejaar3));

        view.findViewById((R.id.jaar4)).setOnClickListener(v ->
                NavHostFragment.findNavController(Studiejaar.this)
                        .navigate(R.id.action_studiejaarFragment_to_studiejaar4));
    }

}
