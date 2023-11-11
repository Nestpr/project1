package org.example.control;

import org.example.model.Location;
import org.example.control.OpenWeatherMapSupplier;
import org.example.model.Weather;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

public interface WeatherSupplier {
	public String getUrl(Location location);

	public String getServerResponse(Location location) throws IOException;

	public List<Weather> getWeatherList(Location location, List<Instant> instantList) throws IOException;

}
