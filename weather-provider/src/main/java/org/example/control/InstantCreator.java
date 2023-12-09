package org.example.control;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
public class InstantCreator {
	public static List<Instant> generateInstantListAtHour(int hour, int numberOfDays) {
		List<Instant> instantList = new ArrayList<>();
		LocalDateTime currentDateTime = LocalDateTime.now();
		currentDateTime = currentDateTime.withHour(hour).withMinute(0).withSecond(0);

		for (int i = 0; i < numberOfDays; i++) {
			Instant instant = currentDateTime.toInstant(ZoneOffset.UTC);
			instantList.add(instant);
			currentDateTime = currentDateTime.plusDays(1);
		}
		return instantList;
	}
}
