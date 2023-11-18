package org.example.control;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.model.Location;
import org.example.model.Weather;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OpenWeatherMapSupplier implements WeatherSupplier {
	private static String weatherTemplate = "https://api.openweathermap.org/data/2.5/forecast?";

	public void setWeatherTemplate(String weatherTemplate) {
		OpenWeatherMapSupplier.weatherTemplate = weatherTemplate;
	}

	public List<Weather> getWeather(Location location, List<Instant> instantList){
		String url = this.getUrl(location);
		String body = null;
		try {
			body = this.getServerResponse(location, url);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		List<Weather> weatherList = null;
		try {
			weatherList = this.getWeatherList(location, instantList, url, body);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return weatherList;
	}

	public String getUrl(Location location) {
		String longitudStr = Double.toString(location.getLongitude());
		String latitudStr = Double.toString(location.getLatitude());

		String url = weatherTemplate + "lat=" + longitudStr + "&lon=" + latitudStr + "&appid=" + System.getenv("API") + "&units=metric";

		return url;
	}

	public String getServerResponse(Location location, String url) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpGet httpGet = new HttpGet(getUrl(location));

		HttpResponse response = httpClient.execute(httpGet);


		String responseBody = EntityUtils.toString(response.getEntity());

		httpClient.close();

		return responseBody;

	}

	public List<Weather> getWeatherList(Location location, List<Instant> instantList,String url, String body) throws IOException {
		String jsonString = getServerResponse(location, url);
		List<Weather> weatherList = new ArrayList<>();
		List<Long> longList = new ArrayList<>();
		for (Instant instant : instantList) {
			longList.add(instant.getEpochSecond());
		}
		try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
			JsonObject jsonObject = reader.readObject();
			List<JsonValue> weathers = jsonObject.getJsonArray("list");
			for (int i = 0; i < 40; i++) {
				JsonObject weather = (JsonObject) weathers.get(i);
				long time = weather.getJsonNumber("dt").longValue();
				if (longList.contains(time)) {
					double rain = weather.getJsonNumber("pop").doubleValue();
					JsonObject windobject = weather.getJsonObject("wind");
					double wind = windobject.getJsonNumber("speed").doubleValue();
					JsonObject mainObject = weather.getJsonObject("main");
					double temperature = mainObject.getJsonNumber("temp").doubleValue();
					double humidity = mainObject.getJsonNumber("humidity").doubleValue();
					Instant instant = Instant.ofEpochSecond(time);
					weatherList.add(new Weather(instant, rain, wind, temperature, humidity, location));
				}
			}

			return weatherList;
		}

	}

}