package com.example.bida.Model;

public class ChuTiemSpinner {
    private String hinh;
    private int id;
    private String hoten;

    public ChuTiemSpinner(String hinh, int id, String hoten) {
        this.hinh = hinh;
        this.id = id;
        this.hoten = hoten;
    }
    public ChuTiemSpinner() {
        this.hinh = hinh;
        this.id = id;
        this.hoten = hoten;
    }
    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}
