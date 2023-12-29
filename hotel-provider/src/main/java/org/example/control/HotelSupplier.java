package org.example.control;
import org.example.model.Hotel;
import org.example.model.HotelKey;
import java.util.List;
public interface HotelSupplier {
	List<Hotel> getHotels(List<HotelKey> hotelKeys, String checkOutDate);
}
