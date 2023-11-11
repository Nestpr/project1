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

import static org.example.control.InstantCreatorList.generateInstantListAtHour;
import static org.example.control.OpenWeatherMapSupplier.*;

public class Main {
	public static void main(String[] args) throws IOException {

		/*
		List<Location> islands = new ArrayList<>();
		islands.add(new Location(28.109869931492124, -15.454238136810305, "Las Palmas de Gran Canaria"));
		islands.add(new Location(28.433615434096133, -16.303736508244132, "Santa Cruz de Tenerife"));
		islands.add(new Location(28.08495328766926, -17.123989892401234, "San Sebastian de La Gomera"));
		islands.add(new Location(28.677729451591084, -17.77365932870263, "Santa Cruz de la Palma"));
		islands.add(new Location(28.677729451591084, -17.77365932870263, "Villa de Valverde"));
		*/


		List<Instant> instantList = generateInstantListAtHour(12, 5);


		//List<Long> longList = new ArrayList<>();

		//for (Instant instant : instantList) {
		//	longList.add(instant.getEpochSecond());
		//}

		Location location = new Location(28.109869931492124, -15.454238136810305, "Las Palmas de Gran Canaria");

		List<Weather> weatherList = getWeatherList(location, instantList);

		Weather weather1 = weatherList.get(0);

		System.out.println(weather1.humidity);


	}

}