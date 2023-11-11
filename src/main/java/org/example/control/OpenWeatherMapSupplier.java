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
	private static String appiKey = "82272d9470b9aa37fb9c5fa221ea12be";
	private static String weatherTemplate = "https://api.openweathermap.org/data/2.5/forecast?";

	public void setWeatherTemplate(String weatherTemplate) {
		OpenWeatherMapSupplier.weatherTemplate = weatherTemplate;
	}

	public static void setAppiKey(String appiKey) {
		OpenWeatherMapSupplier.appiKey = appiKey;
	}

	public String getUrl(Location location) {
		String longitudStr = Double.toString(location.getLongitude());
		String latitudStr = Double.toString(location.getLatitude());

		String url = weatherTemplate + "lat=" + longitudStr + "&lon=" + latitudStr + "&appid=" + appiKey + "&units=metric";

		return url;
	}

	public String getServerResponse(Location location) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpGet httpGet = new HttpGet(getUrl(location));

		HttpResponse response = httpClient.execute(httpGet);


		String responseBody = EntityUtils.toString(response.getEntity());

		httpClient.close();

		return responseBody;

	}

	public List<Weather> getWeatherList(Location location, List<Instant> instantList) throws IOException {
		String jsonString = getServerResponse(location);
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