package org.example.model;

public class HotelKey {
	public String name;
	public String key;
	public String island;
	public HotelKey(String name, String key, String island) {
		this.name = name;
		this.key = key;
		this.island = island;
	}
	public String getKey() {
		return key;
	}
}
