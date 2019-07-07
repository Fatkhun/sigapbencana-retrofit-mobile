package com.example.bahroel.sigapbencana.network.model;

import com.google.gson.annotations.SerializedName;

public class Berita {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("bencana")
	private Bencana bencana;

	@SerializedName("bencana_id")
	private String bencanaId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("judul")
	private String judul;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setBencana(Bencana bencana){
		this.bencana = bencana;
	}

	public Bencana getBencana(){
		return bencana;
	}

	public void setBencanaId(String bencanaId){
		this.bencanaId = bencanaId;
	}

	public String getBencanaId(){
		return bencanaId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	@Override
 	public String toString(){
		return 
			"Berita{" +
			"updated_at = '" + updatedAt + '\'' + 
			",bencana = '" + bencana + '\'' + 
			",bencana_id = '" + bencanaId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",deskripsi = '" + deskripsi + '\'' + 
			",judul = '" + judul + '\'' + 
			"}";
		}
}