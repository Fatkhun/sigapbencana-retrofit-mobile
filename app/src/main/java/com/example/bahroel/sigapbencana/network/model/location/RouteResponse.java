package com.example.bahroel.sigapbencana.network.model.location;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class RouteResponse{

	@SerializedName("routes")
	private List<RoutesItem> routes;

	@SerializedName("geocoded_waypoints")
	private List<GeocodedWaypointsItem> geocodedWaypoints;

	@SerializedName("status")
	private String status;

	public void setRoutes(List<RoutesItem> routes){
		this.routes = routes;
	}

	public List<RoutesItem> getRoutes(){
		return routes;
	}

	public void setGeocodedWaypoints(List<GeocodedWaypointsItem> geocodedWaypoints){
		this.geocodedWaypoints = geocodedWaypoints;
	}

	public List<GeocodedWaypointsItem> getGeocodedWaypoints(){
		return geocodedWaypoints;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"RouteResponse{" + 
			"routes = '" + routes + '\'' + 
			",geocoded_waypoints = '" + geocodedWaypoints + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}