package com.example.bida.Model;

public class PhieuNhap {
    private int id;
    private String ngay;
    private int idnhanvien;
    private String tennhanvien;
    private int iddiadiem;
    private int tienthanhtoan;
    private int trangthai;

    public PhieuNhap(int id, String ngay, int idnhanvien, String tennhanvien, int iddiadiem, int tienthanhtoan, int trangthai) {
        this.id = id;
        this.ngay = ngay;
        this.idnhanvien = idnhanvien;
        this.tennhanvien = tennhanvien;
        this.iddiadiem = iddiadiem;
        this.tienthanhtoan = tienthanhtoan;
        this.trangthai = trangthai;
    }
    public PhieuNhap() {
        this.id = id;
        this.ngay = ngay;
        this.idnhanvien = idnhanvien;
        this.tennhanvien = tennhanvien;
        this.iddiadiem = iddiadiem;
        this.tienthanhtoan = tienthanhtoan;
        this.trangthai = trangthai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getIdnhanvien() {
        return idnhanvien;
    }

    public void setIdnhanvien(int idnhanvien) {
        this.idnhanvien = idnhanvien;
    }

    public String getTennhanvien() {
        return tennhanvien;
    }

    public void setTennhanvien(String tennhanvien) {
        this.tennhanvien = tennhanvien;
    }

    public int getIddiadiem() {
        return iddiadiem;
    }

    public void setIddiadiem(int iddiadiem) {
        this.iddiadiem = iddiadiem;
    }

    public int getTienthanhtoan() {
        return tienthanhtoan;
    }

    public void setTienthanhtoan(int tienthanhtoan) {
        this.tienthanhtoan = tienthanhtoan;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
