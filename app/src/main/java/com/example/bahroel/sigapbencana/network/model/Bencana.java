package com.example.bahroel.sigapbencana.network.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Bencana{

	@SerializedName("detail_info_lokasi")
	private String detailInfoLokasi;

	@SerializedName("image_bencana")
	private List<Object> imageBencana;

	@SerializedName("luka_luka")
	private Object lukaLuka;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("belum_ditemukan")
	private Object belumDitemukan;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("kategori_id")
	private int kategoriId;

	@SerializedName("status_id")
	private int statusId;

	@SerializedName("meninggal")
	private Object meninggal;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("users_id")
	private int usersId;

	@SerializedName("id")
	private int id;

	@SerializedName("mengungsi")
	private Object mengungsi;

	@SerializedName("user")
	private User user;

	public void setDetailInfoLokasi(String detailInfoLokasi){
		this.detailInfoLokasi = detailInfoLokasi;
	}

	public String getDetailInfoLokasi(){
		return detailInfoLokasi;
	}

	public void setImageBencana(List<Object> imageBencana){
		this.imageBencana = imageBencana;
	}

	public List<Object> getImageBencana(){
		return imageBencana;
	}

	public void setLukaLuka(Object lukaLuka){
		this.lukaLuka = lukaLuka;
	}

	public Object getLukaLuka(){
		return lukaLuka;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setBelumDitemukan(Object belumDitemukan){
		this.belumDitemukan = belumDitemukan;
	}

	public Object getBelumDitemukan(){
		return belumDitemukan;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setKategoriId(int kategoriId){
		this.kategoriId = kategoriId;
	}

	public int getKategoriId(){
		return kategoriId;
	}

	public void setStatusId(int statusId){
		this.statusId = statusId;
	}

	public int getStatusId(){
		return statusId;
	}

	public void setMeninggal(Object meninggal){
		this.meninggal = meninggal;
	}

	public Object getMeninggal(){
		return meninggal;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUsersId(int usersId){
		this.usersId = usersId;
	}

	public int getUsersId(){
		return usersId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setMengungsi(Object mengungsi){
		this.mengungsi = mengungsi;
	}

	public Object getMengungsi(){
		return mengungsi;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"Bencana{" + 
			"detail_info_lokasi = '" + detailInfoLokasi + '\'' + 
			",image_bencana = '" + imageBencana + '\'' + 
			",luka_luka = '" + lukaLuka + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",belum_ditemukan = '" + belumDitemukan + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",kategori_id = '" + kategoriId + '\'' + 
			",status_id = '" + statusId + '\'' + 
			",meninggal = '" + meninggal + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",users_id = '" + usersId + '\'' + 
			",id = '" + id + '\'' + 
			",mengungsi = '" + mengungsi + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}