package org.example.model;
import java.time.Instant;

public class Weather {
	public Instant timeInstant;
	public double rain;
	public double wind;
	public double temperature;
	public double humidity;
	public Location location;

	public Weather(Instant timeInstant, double rain, double wind, double temperature, double humidity, Location location) {
		this.timeInstant = timeInstant;
		this.rain = rain;
		this.wind = wind;
		this.temperature = temperature;
		this.humidity = humidity;
		this.location = location;
	}
}
