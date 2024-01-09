package com.example.ruo.pojo.peta;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("medsos_psikolog")
	private String medsosPsikolog;

	@SerializedName("like")
	private Object like;

	@SerializedName("dislike")
	private Object dislike;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("no_telp_psikolog")
	private String noTelpPsikolog;

	@SerializedName("spesialis_psikolog")
	private String spesialisPsikolog;

	@SerializedName("alamat_lengkap")
	private String alamatLengkap;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("nama_psikolog")
	private String namaPsikolog;

	@SerializedName("foto_psikolog")
	private String fotoPsikolog;

	@SerializedName("lama_karir")
	private int lamaKarir;

	@SerializedName("id_therapy")
	private int idTherapy;

	public String getMedsosPsikolog(){
		return medsosPsikolog;
	}

	public Object getLike(){
		return like;
	}

	public Object getDislike(){
		return dislike;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getIdUser(){
		return idUser;
	}

	public String getNoTelpPsikolog(){
		return noTelpPsikolog;
	}

	public String getSpesialisPsikolog(){
		return spesialisPsikolog;
	}

	public String getAlamatLengkap(){
		return alamatLengkap;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getNamaPsikolog(){
		return namaPsikolog;
	}

	public String getFotoPsikolog(){
		return fotoPsikolog;
	}

	public int getLamaKarir(){
		return lamaKarir;
	}

	public int getIdTherapy(){
		return idTherapy;
	}
}