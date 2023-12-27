package com.example.ruo.pojo.Therapy;

import com.google.gson.annotations.SerializedName;

public class AllTherapyItem{

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

	@SerializedName("alamat_lengkap")
	private String alamat_lengkap;

	private int ImgLike;
	private int ImgDislike;

	private int ImgAlamat;

	public AllTherapyItem(int idTherapy, String fotoPsikolog, int lamaKarir, String namaPsikolog, String spesialisPsikolog, String noTelpPsikolog, String medsosPsikolog , int imgLike, int imgDislike, int like, int dislike, String alamat_lengkap, int imgAlamat) {

		this.idTherapy = idTherapy;
		this.fotoPsikolog = fotoPsikolog;
		this.namaPsikolog = namaPsikolog;
		this.spesialisPsikolog = spesialisPsikolog;
		this.lamaKarir = lamaKarir;
		this.noTelpPsikolog = noTelpPsikolog;
		this.medsosPsikolog = medsosPsikolog;
		this.ImgLike = imgLike;
		this.ImgDislike = imgDislike;
		this.like = like;
		this.dislike = dislike;
		this.alamat_lengkap = alamat_lengkap;
		this.ImgAlamat = imgAlamat;
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

	public int getImgLike() {return ImgLike;}

	public int getImgDislike() {return ImgDislike;}

	public String getAlamatLengkap(){
		return alamat_lengkap;
	}




}