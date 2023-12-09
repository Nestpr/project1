package org.example.model;
import java.time.Instant;

public class Weather {
	public Instant predictionInstant;
	public double rain;
	public double wind;
	public double temperature;
	public double humidity;
	public Location location;
	public Instant timeInstant;
	public String ss;
	public Weather(Instant predictionInstant, double rain, double wind, double temperature, double humidity, Location location, Instant timeInstant, String ss) {
		this.predictionInstant = predictionInstant;
		this.rain = rain;
		this.wind = wind;
		this.temperature = temperature;
		this.humidity = humidity;
		this.location = location;
		this.timeInstant = timeInstant;
		this.ss = ss;
	}
}
