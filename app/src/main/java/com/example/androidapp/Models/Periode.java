package com.example.androidapp.Models;

import java.util.ArrayList;

public class Periode {
    ArrayList<Vak> vakken;

    public Periode(ArrayList<Vak> vakken) {
        this.vakken = vakken;
    }

    public ArrayList<Vak> getVakken() {
        return vakken;
    }

    public void setVakken(ArrayList<Vak> vakken) {
        this.vakken = vakken;
    }

    public void setVak(Vak vak){
        this.vakken.add(vak);
    }

    public void removeVak(Vak vak){
        this.vakken.remove(vak);
    }

}
