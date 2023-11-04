package org.example.model;
import java.time.Instant;

public class Weather {
	public Instant timeInstant;
	public int rain;
	public int wind;
	public int temperature;
	public int humidity;
	public Location location;

	public Weather(Instant timeInstant, int rain, int wind, int temperature, int humidity, Location location) {
		this.timeInstant = timeInstant;
		this.rain = rain;
		this.wind = wind;
		this.temperature = temperature;
		this.humidity = humidity;
		this.location = location;
	}
}
