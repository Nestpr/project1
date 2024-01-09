package org.example;
import java.util.Scanner;
public class Main3 {
	public static void main(String[] args) {
		String brokerURL = args[0];
		String jdbcUrl = args[1];
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
		TravelRecommendationBuilder travelRecommendationBuilder = new TravelRecommendationBuilder(jdbcUrl);
		TopicSubscriber topicSubscriber = new TopicSubscriber(brokerURL, travelRecommendationBuilder, sharedNumber);
		topicSubscriber.start();
	}
}