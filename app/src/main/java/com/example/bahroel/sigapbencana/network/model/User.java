package com.example.bahroel.sigapbencana.network.model;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("role_id")
	private String roleId;

	@SerializedName("admin_sar")
	private Object adminSar;

	@SerializedName("lurah")
	private Lurah lurah;

	@SerializedName("api_token")
	private String apiToken;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	public Lurah getLurah() {
		return lurah;
	}

	public void setLurah(Lurah lurah) {
		this.lurah = lurah;
	}

	public void setRoleId(String roleId){
		this.roleId = roleId;
	}

	public String getRoleId(){
		return roleId;
	}

	public void setAdminSar(Object adminSar){
		this.adminSar = adminSar;
	}

	public Object getAdminSar(){
		return adminSar;
	}

	public void setApiToken(String apiToken){
		this.apiToken = apiToken;
	}

	public String getApiToken(){
		return apiToken;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"role_id = '" + roleId + '\'' + 
			",admin_sar = '" + adminSar + '\'' + 
			",api_token = '" + apiToken + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}