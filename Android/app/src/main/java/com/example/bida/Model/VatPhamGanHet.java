package com.example.bida.Model;

public class VatPhamGanHet {
    String tenvatpham;
    int soluong;

    public VatPhamGanHet(String tenvatpham, int soluong) {
        this.tenvatpham = tenvatpham;
        this.soluong = soluong;
    }
    public VatPhamGanHet() {
        this.tenvatpham = tenvatpham;
        this.soluong = soluong;
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
}
