package com.example.bida.Model;

public class Booking {
    private int id;
    private int idkhachhang;
    private String tenkhachhang;
    private String sdt;
    private String ngay;
    private String gio;
    private int trangthai;
    private int idnhanvien;
    private String tennhanvien;
    private int tienthanhtoan;
    private int iddiadiem;
    private String qrcode;
    private int slnguoi;
    private int idban;
    private String tenban;
    private int giaban;
    private String ngaychoi;
    private String giochoi;
    private String giochoithat;

    public Booking(int id, int idkhachhang, String tenkhachhang, String sdt, String ngay,String gio, int trangthai, int idnhanvien, String tennhanvien, int tienthanhtoan, int iddiadiem, String qrcode, int slnguoi, int idban, String tenban, int giaban, String ngaychoi, String giochoi,String giochoithat) {
        this.id = id;
        this.idkhachhang = idkhachhang;
        this.tenkhachhang = tenkhachhang;
        this.sdt = sdt;
        this.ngay = ngay;
        this.gio = gio;
        this.trangthai = trangthai;
        this.idnhanvien = idnhanvien;
        this.tennhanvien = tennhanvien;
        this.tienthanhtoan = tienthanhtoan;
        this.iddiadiem = iddiadiem;
        this.qrcode = qrcode;
        this.slnguoi = slnguoi;
        this.idban = idban;
        this.tenban = tenban;
        this.giaban = giaban;
        this.ngaychoi = ngaychoi;
        this.giochoi = giochoi;
        this.giochoithat = giochoithat;
    }
    public Booking() {
        this.id = id;
        this.idkhachhang = idkhachhang;
        this.tenkhachhang = tenkhachhang;
        this.sdt = sdt;
        this.ngay = ngay;
        this.gio = gio;
        this.trangthai = trangthai;
        this.idnhanvien = idnhanvien;
        this.tennhanvien = tennhanvien;
        this.tienthanhtoan = tienthanhtoan;
        this.iddiadiem = iddiadiem;
        this.qrcode = qrcode;
        this.slnguoi = slnguoi;
        this.idban = idban;
        this.tenban = tenban;
        this.giaban = giaban;
        this.ngaychoi = ngaychoi;
        this.giochoi = giochoi;
        this.giochoithat = giochoithat;
    }

    public String getGiochoithat() {
        return giochoithat;
    }

    public void setGiochoithat(String giochoithat) {
        this.giochoithat = giochoithat;
    }

    public String getNgaychoi() {
        return ngaychoi;
    }

    public void setNgaychoi(String ngaychoi) {
        this.ngaychoi = ngaychoi;
    }

    public String getGiochoi() {
        return giochoi;
    }

    public void setGiochoi(String giochoi) {
        this.giochoi = giochoi;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdkhachhang() {
        return idkhachhang;
    }

    public void setIdkhachhang(int idkhachhang) {
        this.idkhachhang = idkhachhang;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
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

    public int getTienthanhtoan() {
        return tienthanhtoan;
    }

    public void setTienthanhtoan(int tienthanhtoan) {
        this.tienthanhtoan = tienthanhtoan;
    }

    public int getIddiadiem() {
        return iddiadiem;
    }

    public void setIddiadiem(int iddiadiem) {
        this.iddiadiem = iddiadiem;
    }
    public int getSlnguoi() {
        return slnguoi;
    }

    public void setSlnguoi(int slnguoi) {
        this.slnguoi = slnguoi;
    }

    public int getIdban() {
        return idban;
    }

    public void setIdban(int idban) {
        this.idban = idban;
    }

    public String getTenban() {
        return tenban;
    }

    public void setTenban(String tenban) {
        this.tenban = tenban;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }
}
