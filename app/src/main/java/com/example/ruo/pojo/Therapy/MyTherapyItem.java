package com.example.ruo.pojo.Therapy;

import com.google.gson.annotations.SerializedName;

public class MyTherapyItem{

	@SerializedName("medsos_psikolog")
	private String medsosPsikolog;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("like")
	private int like;

	@SerializedName("dislike")
	private int dislike;

	@SerializedName("nama_psikolog")
	private String namaPsikolog;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("foto_psikolog")
	private String fotoPsikolog;

	@SerializedName("lama_karir")
	private int lamaKarir;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("id_therapy")
	private int idTherapy;

	@SerializedName("no_telp_psikolog")
	private String noTelpPsikolog;

	@SerializedName("spesialis_psikolog")
	private String spesialisPsikolog;

	private int imgEdit;
	private int imgDel;

	private int ImgLike;
	private int ImgDislike;

	public MyTherapyItem(int idTherapy, int imgEdit, int imgDel, String fotoPsikolog, String namaPsikolog, String spesialisPsikolog, int lamaKarir, String noTelpPsikolog, String medsosPsikolog, int imgLike, int imgDislike, int jumlahLike, int jumlahDislike) {
		this.idTherapy = idTherapy;
		this.imgEdit = imgEdit;
		this.imgDel = imgDel;
		this.fotoPsikolog = fotoPsikolog;
		this.namaPsikolog = namaPsikolog;
		this.spesialisPsikolog = spesialisPsikolog;
		this.lamaKarir = lamaKarir;
		this.noTelpPsikolog = noTelpPsikolog;
		this.medsosPsikolog = medsosPsikolog;
		this.ImgLike = imgLike;
		this.ImgDislike = imgDislike;
		this.like = jumlahLike;
		this.dislike = jumlahDislike;
	}

	public String getMedsosPsikolog(){
		return medsosPsikolog;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getLike(){
		return like;
	}

	public int getDislike(){
		return dislike;
	}

	public String getNamaPsikolog(){
		return namaPsikolog;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getFotoPsikolog(){
		return fotoPsikolog;
	}

	public int getLamaKarir(){
		return lamaKarir;
	}

	public int getIdUser(){
		return idUser;
	}

	public int getIdTherapy(){
		return idTherapy;
	}

	public String getNoTelpPsikolog(){
		return noTelpPsikolog;
	}

	public String getSpesialisPsikolog(){
		return spesialisPsikolog;
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

}