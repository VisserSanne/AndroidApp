package com.example.androidapp.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.androidapp.Models.Vak;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DataHandler {
    private static DataHandler mInstance;

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DataHandler(Context ctx) {

    }

    public static synchronized DataHandler getHandler(Context ctx) {
        if (mInstance == null) {
            mInstance = new DataHandler(ctx);
        }
        return mInstance;
    }

    public static synchronized DataHandler getHandler() {
        return mInstance;
    }

    public void addClass(String studiejaarString, String periodeString, Vak vak) {
        int studiejaar = Integer.parseInt(studiejaarString.replaceAll("[^\\d.]", ""));
        int periode = Integer.parseInt(periodeString.replaceAll("[^\\d.]", ""));

        Map<String, Object> newVak = new HashMap<>();
        newVak.put("Cijfer", vak.getCijfer());
        newVak.put("EC", vak.getEc());
        newVak.put("Gehaald?", vak.isGehaald());
        newVak.put("Herkansing1", vak.getH1cijfer());
        newVak.put("Herkansing?", vak.isHerkansing());
        newVak.put("Keuzevak?", vak.isKeuzevak());
        newVak.put("Notitie", vak.getNotitie());
        newVak.put("Volgend?", vak.isVolgend());


        db.collection("Username").document("Studiejaar" + studiejaar)
                .collection(String.valueOf(periode)).document(vak.getVakcode()).set(newVak);
    }


    public interface ClassRetrievedFunc {
        void onClassesRetrieved(ArrayList<Vak> Classes);
    }

    public enum Timespan {
        ALL,
        YEAR,
        PERIOD
    }

    public void getClasses(final ClassRetrievedFunc onDataRetrieved, Timespan timespan, int... yearAndPeriod) {
        Integer year = yearAndPeriod.length > 0 ? yearAndPeriod[0] : 0;
        Integer period = yearAndPeriod.length > 1 ? yearAndPeriod[1] : 0;
        List<Task<List<Vak>>> tasks = new ArrayList<>();
                switch (timespan){
            case ALL:
                tasks = getAllClasses();
                break;
            case YEAR:
                tasks = getClassesByYear(year);
                break;
            case PERIOD:
                tasks = getClassesByPeriod(year,period);
                break;
        }

        Task<List<List<Vak>>> finalTask = Tasks.whenAllSuccess(tasks);
        finalTask.addOnCompleteListener(aggregatedTask -> {
            ArrayList<Vak> vakken = new ArrayList<>();
            List<List<Vak>> allVakken = aggregatedTask.getResult();
            for (List<Vak> periodeVakken : allVakken) {
                vakken.addAll(periodeVakken);
            }
            onDataRetrieved.onClassesRetrieved(vakken);
        });
    }

    private List<Task<List<Vak>>> getAllClasses() {
        List<Task<List<Vak>>> tasks = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            tasks.addAll(getClassesByYear( i));
        }
        return tasks;
    }

    private List<Task<List<Vak>>> getClassesByYear(int year) {
        List<Task<List<Vak>>> tasks = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            tasks.addAll( getClassesByPeriod(year, i));
        }
        return tasks;
    }

    private List<Task<List<Vak>>> getClassesByPeriod(int year, int period) {
        List<Task<List<Vak>>> tasks = new ArrayList<>();
        tasks.add(FetchFromFirebase(year,period));
        return tasks;
    }

    public Task<List<Vak>> FetchFromFirebase(int year, int period) {
        return db.collection("Username")
                .document("Studiejaar" + year)
                .collection(String.valueOf(period))
                .get()
                .onSuccessTask(queryDocumentSnapshots -> {
                    ArrayList<Vak> vakken = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        if (document.exists()) {
                            vakken.add(new Vak(document.getId(), document.getBoolean("Keuzevak?"),
                                    document.getDouble("EC"), document.getDouble("Cijfer"),
                                    document.getBoolean("Gehaald?"), document.getBoolean("Herkansing?"),
                                    document.getDouble("Herkansing1"), document.getString("Notitie"),
                                    document.getBoolean("Volgend?"), "Studiejaar " + year, "Periode " + period));
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                    return Tasks.forResult(vakken);
                });
    }

}
