package org.example.control;

import org.example.model.Weather;

import java.util.List;

public interface WeatherStore {
	public void storeWeather(List<Weather>weathers);
}
