package com.example.androidapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Vak implements Parcelable {
    private String vakcode;
    private double cijfer;
    private boolean keuzevak;
    private boolean gehaald;
    private boolean herkansing;
    private double h1cijfer;
    private double ec;
    private String notitie;
    private boolean volgend;

    private String jaar;
    private String periode;

    public Vak(){}

    public Vak(String vakcode, boolean keuzevak, double ec, String jaar, String periode) {
        this.vakcode = vakcode;
        this.keuzevak = keuzevak;
        this.ec = ec;
        this.jaar = jaar;
        this.periode = periode;
    }

    public Vak(String vakcode, boolean keuzevak, double ec, double cijfer, boolean gehaald,
               boolean herkansing, double h1cijfer, String notitie, boolean volgend, String jaar, String periode) {
        this.vakcode = vakcode;
        this.keuzevak = keuzevak;
        this.ec = ec;
        this.cijfer = cijfer;
        this.gehaald = gehaald;
        this.herkansing = herkansing;
        this.h1cijfer = h1cijfer;
        this.notitie = notitie;
        this.volgend = volgend;
        this.jaar = jaar;
        this.periode = periode;
    }

    public Vak(Parcel in) {
        String[] data = new String[11];
        in.readStringArray(data);

        this.vakcode = data[0];
        this.keuzevak = Boolean.parseBoolean(data[1]);
        this.ec = Double.parseDouble(data[2]);
        this.cijfer = Double.parseDouble(data[3]);
        this.gehaald = Boolean.parseBoolean(data[4]);
        this.herkansing = Boolean.parseBoolean(data[5]);
        this.h1cijfer = Double.parseDouble(data[6]);
        this.notitie = data[7];
        this.volgend = Boolean.parseBoolean(data[8]);
        this.jaar = data[9];
        this.periode = data[10];
    }

    public String getVakcode() {return vakcode;}

    public void setVakcode(String vakcode) { this.vakcode = vakcode; }

    public double getCijfer() {
        return cijfer;
    }

    public void setCijfer(double cijfer) {
        this.cijfer = cijfer;
    }

    public boolean isKeuzevak() {
        return keuzevak;
    }

    public void setKeuzevak(boolean keuzevak) {
        this.keuzevak = keuzevak;
    }

    public boolean isGehaald() {
        return gehaald;
    }

    public void setGehaald(boolean gehaald) {
        this.gehaald = gehaald;
    }

    public boolean isHerkansing() {
        return herkansing;
    }

    public void setHerkansing(boolean herkansing) {
        this.herkansing = herkansing;
    }

    public double getH1cijfer() {
        return h1cijfer;
    }

    public void setH1cijfer(double h1cijfer) {
        this.h1cijfer = h1cijfer;
    }

    public double getEc() {
        return ec;
    }

    public void setEc(double ec) {
        this.ec = ec;
    }

    public String getNotitie() {
        return notitie;
    }

    public void setNotitie(String notitie) {
        this.notitie = notitie;
    }

    public boolean isVolgend() {
        return volgend;
    }

    public void setVolgend(boolean volgend) {
        this.volgend = volgend;
    }

    public String getJaar() {
        return jaar;
    }

    public void setJaar(String jaar) {
        this.jaar = jaar;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }


    public Integer getCijferInteger() { return (int) this.cijfer; }
    public Integer getH1CijferInteger() { return (int) this.h1cijfer; }
    public Integer getEcInteger() { return (int) this.ec; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
            this.vakcode,
            String.valueOf(this.keuzevak),
            String.valueOf(this.ec),
            String.valueOf(this.cijfer),
            String.valueOf(this.gehaald),
            String.valueOf(this.herkansing),
            String.valueOf(this.h1cijfer),
            this.notitie,
            String.valueOf(this.volgend),
            this.jaar,
            this.periode
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Vak createFromParcel(Parcel in) {
            return new Vak(in);
        }

        @Override
        public Vak[] newArray(int size) {
            return new Vak[size];
        }
    };
}
