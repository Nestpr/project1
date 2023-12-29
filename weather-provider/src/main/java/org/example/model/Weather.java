package org.example.model;
import java.time.Instant;

public class Weather {
	public Instant predictionTime;
	public double rain;
	public double wind;
	public double temperature;
	public double humidity;
	public Location location;
	public Instant ts;
	public String ss;
	public Weather(Instant predictionTime, double rain, double wind, double temperature, double humidity, Location location, Instant ts, String ss) {
		this.predictionTime = predictionTime;
		this.rain = rain;
		this.wind = wind;
		this.temperature = temperature;
		this.humidity = humidity;
		this.location = location;
		this.ts = ts;
		this.ss = ss;
	}
}
