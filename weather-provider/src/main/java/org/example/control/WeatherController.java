package org.example.control;

import org.example.model.Location;
import org.example.model.Weather;
import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherController{
	private List<Location> locations;
	private List<Instant> timeInstant;
	private WeatherSupplier weatherSupplier;
	private WeatherStore weatherStore;
	public WeatherController(List<Location> locations, WeatherSupplier weatherSupplier, WeatherStore weatherStore, List<Instant> timeInstant) {
		this.locations = locations;
		this.weatherSupplier = weatherSupplier;
		this.weatherStore = weatherStore;
		this.timeInstant = timeInstant;
	}
	public void execute(){
		for (Location location : locations) {
			List<Weather> weatherList = weatherSupplier.getWeather(location, timeInstant);
			weatherStore.storeWeather(weatherList);
		}
	}
	public void timer(){
		Timer timer = new Timer();
		long periodo = 6 * 60 * 60 * 1000;
		TimerTask weatherTask = new TimerTask() {
			@Override
			public void run() {
				execute();
				System.out.println("The function was executed");
			}
		};
		timer.schedule(weatherTask, 0, periodo);
	}
}
