package com.example.bida.KNN;

public class DVAnUong {
    private String tentiem;
    private int soluong;
    private double khoangcach;

    public DVAnUong(String tentiem, int soluong, double khoangcach) {
        this.tentiem = tentiem;
        this.soluong = soluong;
        this.khoangcach = khoangcach;
    }
    public DVAnUong() {
        this.tentiem = tentiem;
        this.soluong = soluong;
        this.khoangcach = khoangcach;
    }

    public String getTentiem() {
        return tentiem;
    }

    public void setTentiem(String tentiem) {
        this.tentiem = tentiem;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(double khoangcach) {
        this.khoangcach = khoangcach;
    }
}
