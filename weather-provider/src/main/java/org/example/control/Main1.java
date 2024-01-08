package org.example.control;
import org.example.model.Location;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.example.control.InstantCreator.generateInstantListAtHour;
public class Main1 {
	public static void main(String[] args) {
		String api = args[0];
		String brokerURL = args[1];
		String topicName = args[2];
		List<Location> islands = new ArrayList<>();
		islands.add(new Location(28.126116508278074, -15.447563715116386, "Las Palmas de Gran Canaria"));
		islands.add(new Location(28.433615434096133, -16.303736508244132, "Santa Cruz de Tenerife"));
		islands.add(new Location(28.08495328766926, -17.123989892401234, "San Sebastian de La Gomera"));
		islands.add(new Location(28.677729451591084, -17.77365932870263, "Santa Cruz de la Palma"));
		islands.add(new Location(27.807865590311415, -17.908997952200863, "Villa de Valverde"));
		islands.add(new Location(28.516900861745228, -13.859043734802182, "Puerto del Rosario"));
		islands.add(new Location(28.964137060031142, -13.552181103779592, "Arrecife"));
		islands.add(new Location(27.75802155994051, -15.580228757742239, "Maspalomas"));
		Scanner scanner = new Scanner(System.in);
		int sharedNumber;
		while (true) {
			System.out.print("Enter the number of days (Maximum 5): ");

			while (!scanner.hasNextInt()) {
				System.out.print("Please enter a valid number: ");
				scanner.next();
			}
			int number = scanner.nextInt();
			if (number >= 1 && number <= 5) {
				sharedNumber = number;
				break;
			} else {
				System.out.println("Please enter a number between 1 and 5.");
			}
		}
		List<Instant> instantList = generateInstantListAtHour(12, sharedNumber);
		OpenWeatherMapSupplier openWeatherMapSupplier = new OpenWeatherMapSupplier(api);
		JmsWeatherStore jmsWeatherStore = new JmsWeatherStore(brokerURL, topicName);
		WeatherController weatherController = new WeatherController(islands, openWeatherMapSupplier, jmsWeatherStore, instantList);
		weatherController.timer();
	}
}