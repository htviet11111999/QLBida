package com.example.bida.Model;

public class CTPhieuNhap {
    private int id;
    private int idphieunhap;
    private int idvatpham;
    private String tenvatpham;
    private String hinhanh;
    private int soluong;
    private int dongia;


    public CTPhieuNhap(int id, int idphieunhap, int idvatpham, String tenvatpham, String hinhanh, int soluong, int dongia) {
        this.id = id;
        this.idphieunhap = idphieunhap;
        this.idvatpham = idvatpham;
        this.tenvatpham = tenvatpham;
        this.hinhanh = hinhanh;
        this.soluong = soluong;
        this.dongia = dongia;
    }
    public CTPhieuNhap() {
        this.id = id;
        this.idphieunhap = idphieunhap;
        this.idvatpham = idvatpham;
        this.tenvatpham = tenvatpham;
        this.hinhanh = hinhanh;
        this.soluong = soluong;
        this.dongia = dongia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getIdphieunhap() {
        return idphieunhap;
    }

    public void setIdphieunhap(int idphieunhap) {
        this.idphieunhap = idphieunhap;
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
