package org.example.model;
public class Location {
	double latitude;
	double longitude;
	String island;
	public Location(double latitude, double longitude, String island) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.island = island;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public String getIsland() {
		return island;
	}
}
