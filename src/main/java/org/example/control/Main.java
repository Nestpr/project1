package org.example.control;
import org.example.model.Location;
import org.example.model.Weather;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.model.Weather;
import org.glassfish.json.JsonUtil;

import java.time.Instant;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.json.JsonArray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.example.control.InstantCreatorList.generateInstantListAtHour;
import static org.example.control.OpenWeatherMapSupplier.*;

public class Main {
	public static void main(String[] args) throws IOException {


		List<Location> islands = new ArrayList<>();
		islands.add(new Location(28.126116508278074, -15.447563715116386, "Las Palmas de Gran Canaria"));
		islands.add(new Location(28.433615434096133, -16.303736508244132, "Santa Cruz de Tenerife"));
		islands.add(new Location(28.08495328766926, -17.123989892401234, "San Sebastian de La Gomera"));
		islands.add(new Location(28.677729451591084, -17.77365932870263, "Santa Cruz de la Palma"));
		islands.add(new Location(28.677729451591084, -17.77365932870263, "Villa de Valverde"));
		islands.add(new Location(28.516900861745228, -13.859043734802182, "Puerto del Rosario"));
		islands.add(new Location(28.964137060031142, -13.552181103779592, "Arrecife"));
		islands.add(new Location(29.232734043688364, -13.501880960490942, "Caleta de Sebo"));

		String path = "jdbc:sqlite:weather_database.db";

		List<Instant> instantList = generateInstantListAtHour(12, 5);

		OpenWeatherMapSupplier openWeatherMapSupplier = new OpenWeatherMapSupplier();

		/*
		for (Location location : islands) {

			String url = openWeatherMapSupplier.getUrl(location);
			String body = openWeatherMapSupplier.getServerResponse(location, url);
			List<Weather>weathers = openWeatherMapSupplier.getWeather(location, instantList);
			System.out.println(weathers.get(0));
			System.out.println(weathers.get(1));
			System.out.println(weathers.get(2));
			System.out.println(weathers.get(3));
			System.out.println(weathers.get(4));


		}
		*/

		SQLiteWeatherStore sqLiteWeatherStore = new SQLiteWeatherStore();

		WeatherController weatherController = new WeatherController(islands, openWeatherMapSupplier, sqLiteWeatherStore, instantList);

		weatherController.execute(islands, openWeatherMapSupplier, sqLiteWeatherStore, instantList);
		

	}


}