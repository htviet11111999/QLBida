package com.example.bida.Model;

public class Kho {
    private int id;
    private String tenvatpham;
    private int soluong;
    private int dongia;
    private String hinhanh;
    private int iddiadiem;

    public Kho(int id, String tenvatpham, int soluong, int dongia, String hinhanh, int iddiadiem) {
        this.id = id;
        this.tenvatpham = tenvatpham;
        this.soluong = soluong;
        this.dongia = dongia;
        this.hinhanh = hinhanh;
        this.iddiadiem = iddiadiem;
    }
    public Kho() {
        this.id = id;
        this.tenvatpham = tenvatpham;
        this.soluong = soluong;
        this.dongia = dongia;
        this.hinhanh = hinhanh;
        this.iddiadiem = iddiadiem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenvatpham() {
        return tenvatpham;
    }

    public void setTenvatpham(String tenvatpham) {
        this.tenvatpham = tenvatpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getIddiadiem() {
        return iddiadiem;
    }

    public void setIddiadiem(int iddiadiem) {
        this.iddiadiem = iddiadiem;
    }
}
