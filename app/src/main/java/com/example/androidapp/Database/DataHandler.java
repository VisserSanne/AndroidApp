package com.example.androidapp.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.androidapp.Models.Vak;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
        List<Task<QuerySnapshot>> tasks = new ArrayList<>();
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

        Task<List<QuerySnapshot>> finalTask = Tasks.whenAllSuccess(tasks);
        finalTask.addOnCompleteListener(aggregatedTask -> {
            List<QuerySnapshot> allTasks = aggregatedTask.getResult();
            ArrayList<Vak> vakken = new ArrayList<>();
            for (QuerySnapshot query : allTasks) {
                for (QueryDocumentSnapshot document : query) {
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
            }
            onDataRetrieved.onClassesRetrieved(vakken);
        });
    }

    private List<Task<QuerySnapshot>> getAllClasses() {
        List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            tasks.addAll(getClassesByYear( i));
        }
        return tasks;
    }

    private List<Task<QuerySnapshot>> getClassesByYear(int year) {
        List<Task<QuerySnapshot>> tasks = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            tasks.addAll( getClassesByPeriod(year, i));
        }
        return tasks;
    }

    private List<Task<QuerySnapshot>> getClassesByPeriod(int year, int period) {
        List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        tasks.add(FetchFromFirebase(year,period));
        return tasks;
//        Task<QuerySnapshot> querySnapshotTask = db.collection("Username").document("Studiejaar" + year).collection(String.valueOf(period)).get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            ArrayList<Vak> vakken = new ArrayList<Vak>();
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                if (document.exists()) {
//                                    vakken.add(new Vak(document.getId(), document.getBoolean("Keuzevak?"),
//                                            document.getDouble("EC"), document.getDouble("Cijfer"),
//                                            document.getBoolean("Gehaald?"), document.getBoolean("Herkansing?"),
//                                            document.getDouble("Herkansing1"), document.getString("Notitie"),
//                                            document.getBoolean("Volgend?"), "Studiejaar " + year, "Periode " + period));
//                                } else {
//                                    Log.d(TAG, "No such document");
//                                }
//                            }
//                            onDataRetrieved.onClassesRetrieved(vakken);
//                        } else {
//                            Log.d(TAG, "get failed with ", task.getException());
//                        }
//                    }
//                });
    }

    public Task<QuerySnapshot> FetchFromFirebase(int year, int period) {
        return db.collection("Username").document("Studiejaar" + year).collection(String.valueOf(period)).get();
    }

}
