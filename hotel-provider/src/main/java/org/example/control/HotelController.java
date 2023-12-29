package org.example.control;
import org.example.model.Hotel;
import org.example.model.HotelKey;
import org.example.
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HotelController {
	private List<HotelKey> hotelKeys;
	private String checkOutDate;
	private HotelSupplier hotelSupplier;
	private HotelStore hotelStore;

	public HotelController(List<HotelKey> hotelKeys, String checkOutDate, HotelSupplier hotelSupplier, HotelStore hotelStore) {
		this.hotelKeys = hotelKeys;
		this.checkOutDate = checkOutDate;
		this.hotelSupplier = hotelSupplier;
		this.hotelStore = hotelStore;
	}
	public void execute(){
		List<Hotel> hotels = hotelSupplier.getHotels(hotelKeys, checkOutDate);
		hotelStore.storeHotel(hotels);
	}
	public void timer(){
		Timer timer = new Timer();
		long periodo = 6 * 60 * 60 * 1000;
		TimerTask weatherTask = new TimerTask() {
			@Override
			public void run() {
				execute();
				System.out.println("La función se ejecutó");
			}
		};
		timer.schedule(weatherTask, 0, periodo);
	}
}

