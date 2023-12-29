package org.example.control;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.model.Hotel;
import org.example.model.HotelKey;

import javax.json.*;
import java.io.IOException;
import java.io.StringReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class HotelKeyResolver implements HotelSupplier{
	private static String weatherTemplate = "https://data.xotelo.com/api/heatmap?";
	private String checkOutDate;
	public HotelKeyResolver(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public List<Hotel> getHotels(List<HotelKey> hotelKeys, String checkOutDate){
		try {
			List<Hotel> hotels = getHotelsList(hotelKeys, checkOutDate);
			return hotels;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public String getUrl(HotelKey hotelKey) {
		String key = hotelKey.getKey();
		String checkOut = checkOutDate;
		String url = weatherTemplate + "hotel_key=" + key + "&chk_out=" + checkOut;
		return url;
	}

	public String getServerResponse(String url) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		String responseBody = EntityUtils.toString(response.getEntity());
		httpClient.close();
		return responseBody;
	}

	private static String[] convertJsonArrayToStringArray(JsonArray jsonArray) {
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);
		}
		return stringArray;
	}

	public List<Hotel> getHotelsList(List<HotelKey> hotelKeys, String checkOutDate) throws IOException {
		List<Hotel> hotels = new ArrayList<>();
		for (HotelKey hotelKey: hotelKeys) {
			String url = getUrl(hotelKey);
			String response = getServerResponse(url);
			try (JsonReader reader = Json.createReader(new StringReader(response))) {
				JsonObject jsonObject = reader.readObject();
				if (jsonObject.containsKey("result") && !jsonObject.isNull("result")) {
					JsonObject resultObject = jsonObject.getJsonObject("result");
					JsonObject heatmapObject = resultObject.getJsonObject("heatmap");
					JsonArray averagePrice = heatmapObject.getJsonArray("average_price_days");
					JsonArray cheapPrice = heatmapObject.getJsonArray("cheap_price_days");
					JsonArray highPrice = heatmapObject.getJsonArray("high_price_days");
					Instant ts = Instant.now();
					String ss = "Xotelo";
					String[] averagePriceDays = convertJsonArrayToStringArray(averagePrice);
					String[] cheapPriceDays = convertJsonArrayToStringArray(cheapPrice);
					String[] highPriceDays = convertJsonArrayToStringArray(highPrice);
					Hotel hotel = new Hotel(hotelKey, checkOutDate, averagePriceDays, cheapPriceDays, highPriceDays, ts, ss);
					hotels.add(hotel);

				}else{
					Instant ts = Instant.now();
					String ss = "Xotelo";
					Hotel hotel = new Hotel(hotelKey, checkOutDate, null, null, null, ts, ss);
					hotels.add(hotel);
				}
			}
		}
		return hotels;
	}

}
