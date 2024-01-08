package org.example.control;
import org.example.model.Weather;

import java.util.List;

public interface WeatherStore {
	void storeWeather(List<Weather>weathers);
}
