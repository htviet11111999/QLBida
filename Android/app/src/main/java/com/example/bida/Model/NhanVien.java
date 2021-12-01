package com.example.bida.Model;

public class NhanVien {
    private int id;
    private String hoten;
    private String hinhanh;
    private String diachi;
    private int gioitinh;
    private String sdt;
    private int iddiadiem;

    public NhanVien(int id, String hoten, String hinhanh, String diachi, int gioitinh, String sdt, int iddiadiem) {
        this.id = id;
        this.hoten = hoten;
        this.hinhanh = hinhanh;
        this.diachi = diachi;
        this.gioitinh = gioitinh;
        this.sdt = sdt;
        this.iddiadiem = iddiadiem;
    }
    public NhanVien() {
        this.id = id;
        this.hoten = hoten;
        this.hinhanh = hinhanh;
        this.diachi = diachi;
        this.gioitinh = gioitinh;
        this.sdt = sdt;
        this.iddiadiem = iddiadiem;
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

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(int gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getIddiadiem() {
        return iddiadiem;
    }

    public void setIddiadiem(int iddiadiem) {
        this.iddiadiem = iddiadiem;
    }
}
