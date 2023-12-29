package org.example.model;

import java.time.Instant;
import java.util.List;

public class Hotel {
	public HotelKey hotelKey;
	public String checkOutDate;
	public String[] averagePriceDays;
	public String[] cheapPriceDays;
	public String[] highPriceDays;
	public Instant ts;
	public String ss;

	public Hotel(HotelKey hotelKey, String checkOutDate, String[] averagePriceDays, String[] cheapPriceDays, String[] highPriceDays, Instant ts, String ss) {
		this.hotelKey = hotelKey;
		this.checkOutDate = checkOutDate;
		this.averagePriceDays = averagePriceDays;
		this.cheapPriceDays = cheapPriceDays;
		this.highPriceDays = highPriceDays;
		this.ts = ts;
		this.ss = ss;
	}
}
