package com.example.androidapp.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.EditItemPopupActivity;
import com.example.androidapp.Models.Vak;
import com.example.androidproject.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter {

    RecyclerView recyclerView;
    private ArrayList<Vak> allCourses;
    private static final String TAG = "RecyclerAdapter";
    private Activity activityParent;

    public RecyclerAdapter(ArrayList<Vak> vakken, Fragment fragment, View view) {
        allCourses = vakken;
        activityParent = fragment.getActivity();
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerViewHolder thisHolder = (RecyclerViewHolder) holder;
        Vak vak = allCourses.get(position);

        thisHolder.vakCode.setText(vak.getVakcode());
        thisHolder.jaar.setText(vak.getJaar());
        thisHolder.periode.setText(vak.getPeriode());
        thisHolder.ec.setText(vak.getEc() +" EC");
        thisHolder.vakCode.setText(vak.getVakcode());

        if(vak.getCijfer() != 0){
            if(vak.getCijfer() >= vak.getH1cijfer()) {
                thisHolder.cijfer.setText(Double.toString(vak.getCijfer()));
            } else {
                thisHolder.cijfer.setText(Double.toString(vak.getH1cijfer()));
            }
        } else {
            thisHolder.cijfer.setText("X");
        }


        if(vak.isKeuzevak()){
            thisHolder.keuzevak.setText("Keuzevak");
        } else {
            thisHolder.keuzevak.setVisibility(View.GONE);
        }
        if(vak.isGehaald()){
            thisHolder.gehaald.setVisibility(View.VISIBLE);
            thisHolder.nietGehaald.setVisibility(View.GONE);
        } else {
            thisHolder.gehaald.setVisibility(View.GONE);
            thisHolder.nietGehaald.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return allCourses.size();
    }

    private View.OnClickListener mItemAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int itemPosition = recyclerView.indexOfChild(v);

            Vak currVak = allCourses.get(itemPosition);

            Intent intent = new Intent(activityParent, EditItemPopupActivity.class);
            intent.putExtra("vak", currVak);
            activityParent.startActivityForResult(intent, 3);
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View container = LayoutInflater.from(parent.getContext()).inflate(R.layout.vak_layout, parent, false);

        container.setOnClickListener(mItemAction);
        return new RecyclerViewHolder(container);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView vakCode;
        public TextView jaar;
        public TextView periode;
        public TextView ec;
        public TextView cijfer;
        public TextView keuzevak;
        public ImageView gehaald;
        public ImageView nietGehaald;

        public RecyclerViewHolder(View v) {
            super(v);
            LinearLayout container = (LinearLayout) v;

            vakCode = v.findViewById(R.id.vakCode);
            jaar = v.findViewById(R.id.jaar);
            periode = v.findViewById(R.id.periode);
            ec = v.findViewById(R.id.ec);
            cijfer = v.findViewById(R.id.cijfer);
            keuzevak = v.findViewById(R.id.keuzevak);
            gehaald = v.findViewById(R.id.gehaald);
            nietGehaald = v.findViewById(R.id.nietGehaald);
        }
    }
}
