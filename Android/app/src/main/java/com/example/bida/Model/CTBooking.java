package com.example.bida.Model;

public class CTBooking {
    private int id;
    private int idbooking;
    private int idvatpham;
    private String tenvatpham;
    private int soluong;
    private int dongia;
    private String hinhanh;

    public CTBooking(int id, int idbooking, int idvatpham, String tenvatpham, int soluong, int dongia, String hinhanh) {
        this.id = id;
        this.idbooking = idbooking;
        this.idvatpham = idvatpham;
        this.tenvatpham = tenvatpham;
        this.soluong = soluong;
        this.dongia = dongia;
        this.hinhanh = hinhanh;
    }
    public CTBooking() {
        this.id = id;
        this.idbooking = idbooking;
        this.idvatpham = idvatpham;
        this.tenvatpham = tenvatpham;
        this.soluong = soluong;
        this.dongia = dongia;
        this.hinhanh = hinhanh;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdbooking() {
        return idbooking;
    }

    public void setIdbooking(int idbooking) {
        this.idbooking = idbooking;
    }



    public int getIdvatpham() {
        return idvatpham;
    }

    public void setIdvatpham(int idvatpham) {
        this.idvatpham = idvatpham;
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
}
