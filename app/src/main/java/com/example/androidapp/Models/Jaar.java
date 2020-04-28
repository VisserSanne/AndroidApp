package com.example.androidapp.Models;

public class Jaar {
    private Periode periode1;
    private Periode periode2;
    private Periode periode3;
    private Periode periode4;

    public Jaar(Periode periode1, Periode periode2, Periode periode3, Periode periode4) {
        this.periode1 = periode1;
        this.periode2 = periode2;
        this.periode3 = periode3;
        this.periode4 = periode4;
    }

    public Periode getPeriode1() {
        return periode1;
    }

    public void setPeriode1(Periode periode1) {
        this.periode1 = periode1;
    }

    public Periode getPeriode2() {
        return periode2;
    }

    public void setPeriode2(Periode periode2) {
        this.periode2 = periode2;
    }

    public Periode getPeriode3() {
        return periode3;
    }

    public void setPeriode3(Periode periode3) {
        this.periode3 = periode3;
    }

    public Periode getPeriode4() {
        return periode4;
    }

    public void setPeriode4(Periode periode4) {
        this.periode4 = periode4;
    }
}
