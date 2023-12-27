package com.example.ruo.pojo.Therapy;

import com.google.gson.annotations.SerializedName;

public class DataTherapy{
	@SerializedName("medsos_psikolog")
	private String medsosPsikolog;
	private String updatedAt;
	private int like;
	private int dislike;
	@SerializedName("nama_psikolog")
	private String namaPsikolog;
	private String createdAt;
	@SerializedName("foto_psikolog")
	private String fotoPsikolog;
	@SerializedName("lama_karir")
	private int lamaKarir;
	private int idUser;
	@SerializedName("id_therapy")
	private int idTherapy;
	@SerializedName("no_telp_psikolog")
	private String noTelpPsikolog;
	@SerializedName("spesialis_psikolog")
	private String spesialisPsikolog;

	@SerializedName("alamat_lengkap")
	private String alamat_lengkap;

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
	public String getAlamatLengkap(){
		return alamat_lengkap;
	}

}
