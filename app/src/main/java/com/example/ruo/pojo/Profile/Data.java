package com.example.ruo.pojo.Profile;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("password")
	private String password;

	@SerializedName("foto_user")
	private String fotoUser;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("phone")
	private Object phone;

	@SerializedName("medsos")
	private String medsos;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public String getPassword(){
		return password;
	}

	public String getFotoUser(){
		return fotoUser;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public Object getPhone(){
		return phone;
	}

	public String getMedsos(){
		return medsos;
	}
	public String getDescription(){
		return description;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getIdUser(){
		return idUser;
	}

	public String getEmail(){
		return email;
	}

	public String getUsername(){
		return username;
	}
}