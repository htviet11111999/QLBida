package com.example.bida.Model;

public class TaiKhoan {
    private int id;
    private String sdt;
    private String matkhau;
    private String hoten;
    private String hinhanh;
    private String diachi;
    private String quyen;
    private int sohuutiem;

    public TaiKhoan(int id, String sdt, String matkhau, String hoten, String hinhanh, String diachi, String quyen,int sohuutiem) {
        this.id = id;
        this.sdt = sdt;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.hinhanh = hinhanh;
        this.diachi = diachi;
        this.quyen = quyen;
        this.sohuutiem = sohuutiem;
    }
    public TaiKhoan() {
        this.id = id;
        this.sdt = sdt;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.hinhanh = hinhanh;
        this.diachi = diachi;
        this.quyen = quyen;
        this.sohuutiem = sohuutiem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
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

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    public int getSohuutiem() {
        return sohuutiem;
    }

    public void setSohuutiem(int sohuutiem) {
        this.sohuutiem = sohuutiem;
    }
}
