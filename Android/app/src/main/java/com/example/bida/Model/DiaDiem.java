package com.example.bida.Model;

public class DiaDiem {
    private int id;
    private String ten;
    private String diachi;
    private double kinhdo;
    private double vido;
    private int trangthai;
    private String ghichu;
    private int idchu;
    private String hotenchu;
    private int baigiuxe;

    public int getBaigiuxe() {
        return baigiuxe;
    }

    public void setBaigiuxe(int baigiuxe) {
        this.baigiuxe = baigiuxe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getKinhdo() {
        return kinhdo;
    }

    public void setKinhdo(double kinhdo) {
        this.kinhdo = kinhdo;
    }

    public double getVido() {
        return vido;
    }

    public void setVido(double vido) {
        this.vido = vido;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public int getIdchu() {
        return idchu;
    }

    public void setIdchu(int idchu) {
        this.idchu = idchu;
    }

    public String getHotenchu() {
        return hotenchu;
    }

    public void setHotenchu(String hotenchu) {
        this.hotenchu = hotenchu;
    }

    public DiaDiem(int id, String ten, String diachi, double kinhdo, double vido, int trangthai, String ghichu, int idchu, String hotenchu, int baigiuxe) {
        this.id = id;
        this.ten = ten;
        this.diachi = diachi;
        this.kinhdo = kinhdo;
        this.vido = vido;
        this.trangthai = trangthai;
        this.ghichu = ghichu;
        this.idchu = idchu;
        this.hotenchu = hotenchu;
        this.baigiuxe = baigiuxe;
    }
    public DiaDiem() {
        this.id = id;
        this.ten = ten;
        this.diachi = diachi;
        this.kinhdo = kinhdo;
        this.vido = vido;
        this.trangthai = trangthai;
        this.ghichu = ghichu;
        this.idchu = idchu;
        this.hotenchu = hotenchu;
        this.baigiuxe = baigiuxe;
    }




}
