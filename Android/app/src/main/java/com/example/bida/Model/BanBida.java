package com.example.bida.Model;

public class BanBida {
    private int id;
    private String tenban;
    private int gia;
    private int soluong;
    private String hinhanh;
    private int iddiadiem;

    public BanBida() {
        this.id = id;
        this.tenban = tenban;
        this.gia = gia;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
        this.iddiadiem = iddiadiem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenban() {
        return tenban;
    }

    public void setTenban(String tenban) {
        this.tenban = tenban;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
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
