package org.example.control;
import org.example.model.HotelKey;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.example.control.InstantCreator.generateInstantListAtHour;

public class Main2 {
	public static void main(String[] args) {
		String brokerURL = args[0];
		String topicName = args[1];
		List<HotelKey> hotelKeys = new ArrayList<>();

		hotelKeys.add(new HotelKey("Santa Catalina, a Royal Hideaway Hotel", "g187472-d228489", "Las Palmas de Gran Canaria"));
		hotelKeys.add(new HotelKey("Hotel Cristina", "g187472-d228541", "Las Palmas de Gran Canaria"));
		hotelKeys.add(new HotelKey("Occidental Las Palmas", "g187472-d19875360", "Las Palmas de Gran Canaria"));
		hotelKeys.add(new HotelKey("Bull Reina Isabel", "g187472-d231610", "Las Palmas de Gran Canaria"));
		hotelKeys.add(new HotelKey("Veintiuno", "g187472-d13480199", "Las Palmas de Gran Canaria"));
		hotelKeys.add(new HotelKey("Iberostar Heritage Grand Mencey", "g187482-d1555517", "Santa Cruz de Tenerife"));
		hotelKeys.add(new HotelKey("Tivoli La Caleta Tenerife Resort", "g187482-d25325551", "Santa Cruz de Tenerife"));
		hotelKeys.add(new HotelKey("Hotel Colon Rambla", "g187482-d1190472", "Santa Cruz de Tenerife"));
		hotelKeys.add(new HotelKey("Occidental Santa Cruz Contemporaneo", "g187482-d232770", "Santa Cruz de Tenerife"));
		hotelKeys.add(new HotelKey("Hotel Taburiente", "g187482-d529060", "Santa Cruz de Tenerife"));
		hotelKeys.add(new HotelKey("Parador de la Gomera", "g187470-d190895", "San Sebastian de La Gomera"));
		hotelKeys.add(new HotelKey("Hotel Jardin Tecina", "g1187912-d324473", "San Sebastian de La Gomera"));
		hotelKeys.add(new HotelKey("Hotel Gran Rey", "g674782-d616495", "San Sebastian de La Gomera"));
		hotelKeys.add(new HotelKey("Hotel Villa Gomera", "g187470-d614341", "San Sebastian de La Gomera"));
		hotelKeys.add(new HotelKey("Hotel La Colombina", "g187470-d2039274", "San Sebastian de La Gomera"));
		hotelKeys.add(new HotelKey("Hotel Hacienda De Abajo", "g1177806-d3577949", "Santa Cruz de la Palma"));
		hotelKeys.add(new HotelKey("La Palma Princess", "g1175543-d638034", "Santa Cruz de la Palma"));
		hotelKeys.add(new HotelKey("Hacienda San Jorge", "g642213-d535420", "Santa Cruz de la Palma"));
		hotelKeys.add(new HotelKey("Hotel San Telmo", "g187476-d1760436", "Santa Cruz de la Palma"));
		hotelKeys.add(new HotelKey("El Hotelito 27", "g187476-d13942789", "Santa Cruz de la Palma"));
		hotelKeys.add(new HotelKey("Apartahotel Boomerang", "g187474-d1497072", "Villa de Valverde"));
		hotelKeys.add(new HotelKey("Casa Rural El Hondillo", "g187474-d24030803", "Villa de Valverde"));
		hotelKeys.add(new HotelKey("Hostal Casanas", "g187474-d1508928", "Villa de Valverde"));
		hotelKeys.add(new HotelKey("Pension San Fleit", "g187474-d1497235", "Villa de Valverde"));
		hotelKeys.add(new HotelKey("Balneario Pozo de la Salud", "g1189149-d1193818", "Villa de Valverde"));
		hotelKeys.add(new HotelKey("Hotel el Mirador de Fuerteventura", "g1064406-d1166840", "Puerto del Rosario"));
		hotelKeys.add(new HotelKey("JM Puerto Rosario", "g1064406-d1181521", "Puerto del Rosario"));
		hotelKeys.add(new HotelKey("Apartamentos Tao Laia", "g1064406-d15308088", "Puerto del Rosario"));
		hotelKeys.add(new HotelKey("Kikiki House", "g1064406-d12986555", "Puerto del Rosario"));
		hotelKeys.add(new HotelKey("Castillo Mar", "g658907-d1151716", "Puerto del Rosario"));
		hotelKeys.add(new HotelKey("Hotel Lancelot", "g187478-d273097", "Arrecife"));
		hotelKeys.add(new HotelKey("Arrecife Gran Hotel & Spa", "g187478-d507813", "Arrecife"));
		hotelKeys.add(new HotelKey("La Concha Boutique", "g1902255-d12913351", "Arrefice"));
		hotelKeys.add(new HotelKey("Hotel Diamar", "g187478-d1007935", "Arrecife"));
		hotelKeys.add(new HotelKey("Hotel Villa Vik", "g187478-d950977", "Arrecife"));
		hotelKeys.add(new HotelKey("Paradisus by Melia Gran Canaria", "g562818-d238899", "Maspalomas"));
		hotelKeys.add(new HotelKey("Club Maspalomas Suites & Spa", "g230095-d645272", "Maspalomas"));
		hotelKeys.add(new HotelKey("Gloria Palace Thalasso & Hotel", "g562818-d237094", "Maspalomas"));
		hotelKeys.add(new HotelKey("HD Parque Cristobal", "g562819-d600110", "Maspalomas"));
		hotelKeys.add(new HotelKey("Axel Beach Maspalomas", "g562819-d4107099", "Maspalomas"));

		Scanner scanner = new Scanner(System.in);
		int sharedNumber;
		while (true) {
			System.out.print("Vuelva a introducir el número de días (Debe ser igual al introducido anteriormente): ");

			while (!scanner.hasNextInt()) {
				System.out.print("Por favor, ingrese un número válido: ");
				scanner.next();
			}

			int number = scanner.nextInt();

			if (number >= 1 && number <= 5) {
				sharedNumber = number;
				break;
			} else {
				System.out.println("Por favor, ingrese un número entre 1 y 5.");
			}
		}

		List<Instant> instantList = generateInstantListAtHour(12, sharedNumber);
		Instant instant = instantList.get(sharedNumber - 1);
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = localDate.format(formatter);
		HotelKeyResolver hotelKeyResolver = new HotelKeyResolver(date);
		JmsHotelStore jmsHotelStore = new JmsHotelStore(brokerURL, topicName);
		HotelController hotelController = new HotelController(hotelKeys, date, hotelKeyResolver, jmsHotelStore);
		hotelController.timer();
	}
}
