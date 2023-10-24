package com.example.ruo;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyListIndividuTherapyData {


    private int idTherapy;
    private int ImgPsikolog;
    private String nama;
    private String pekerjaan;
    private int lama_kerja;

    private String no_telp;
    private String Instagram;

    private int ImgLike;
    private int ImgDislike;

    private int jumlahLike;

    private  int jumlahUnlike;

    private int imgEdit;
    private int imgDel;

    public MyListIndividuTherapyData(int idTherapy,int imgEdit, int imgDel, int imgPsikolog, String nama, String pekerjaan, int lama_kerja, String no_telp, String instagram, int imgLike, int imgDislike, int jumlahLike, int jumlahUnlike) {
        ImgPsikolog = imgPsikolog;
        this.nama = nama;
        this.pekerjaan = pekerjaan;
        this.lama_kerja = lama_kerja;
        this.no_telp = no_telp;
        Instagram = instagram;
        this.ImgLike = imgLike;
        this.ImgDislike = imgDislike;
        this.jumlahLike = jumlahLike;
        this.imgEdit = imgEdit;
        this.imgDel = imgDel;
        this.jumlahUnlike = jumlahUnlike;
        this.idTherapy = idTherapy;
    }

    public int getImgPsikolog() {
        return ImgPsikolog;
    }

    public void setImgPsikolog(int imgPsikolog) {
        ImgPsikolog = imgPsikolog;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public int getLama_kerja() {
        return lama_kerja;
    }

    public void setLama_kerja(int lama_kerja) {
        this.lama_kerja = lama_kerja;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getInstagram() {
        return Instagram;
    }

    public void setInstagram(String instagram) {
        Instagram = instagram;
    }

    public int getImgLike() {
        return ImgLike;
    }

    public void setImgLike(int imgLike) {
        ImgLike = imgLike;
    }

    public int getImgDislike() {
        return ImgDislike;
    }

    public void setImgDislike(int imgDislike) {
        ImgLike = imgDislike;
    }

    public int getJumlahLike() {
        return jumlahLike;
    }

    public void setJumlahLike(int jumlahLike) {
        this.jumlahLike = jumlahLike;
    }

    public int getJumlahUnlike() {
        return jumlahUnlike;
    }

    public void setJumlahUnlike(int jumlahUnlike) {
        this.jumlahUnlike = jumlahUnlike;
    }

    public int getIdTherapy() {
        return idTherapy;
    }

    public void setIdTherapy(int idTherapy) {
        this.idTherapy = idTherapy;
    }

    public int getIdImgEdit() {
        return imgEdit;
    }

    public void setImgEdit(int imgEdit) {
        this.imgEdit = imgEdit;
    }

    public int getImgDel() {
        return imgDel;
    }

    public void setImgDel(int imgDel) {
        this.imgDel = imgDel;
    }


}
