package com.example.ruo.pojo.message;

import com.google.gson.annotations.SerializedName;

public class AccountItem {

   @SerializedName("id_user")
   private int idUser;

   @SerializedName("foto_user")
   private String fotoUser;

   @SerializedName("username")
   private String username;

   @SerializedName("email")
   private String email;

   @SerializedName("password")
   private String password;

   @SerializedName("phone")
   private String phone;

   @SerializedName("medsos")
   private String medsos;

   @SerializedName("description")
   private String description;

   @SerializedName("fcmToken")
   private String fcmToken;

   public int getIdUser() {
      return idUser;
   }

   public void setIdUser(int idUser) {
      this.idUser = idUser;
   }

   public String getFotoUser() {
      return fotoUser;
   }

   public void setFotoUser(String fotoUser) {
      this.fotoUser = fotoUser;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getMedsos() {
      return medsos;
   }

   public void setMedsos(String medsos) {
      this.medsos = medsos;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getFcmToken() {
      return fcmToken;
   }

   public void setFcmToken(String fcmToken) {
      this.fcmToken = fcmToken;
   }
}