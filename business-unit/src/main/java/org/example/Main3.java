package org.example;

public class Main3 {
	public static void main(String[] args) {
		String brokerURL = args[0];
		String topicName = args[1];
		String clientId = args[2];
		String subscriptionName = args[3];
		TravelRecommendationBuilder travelRecommendationBuilder = new TravelRecommendationBuilder();
		TopicSubscriber topicSubscriber = new TopicSubscriber(brokerURL, topicName, clientId, subscriptionName, travelRecommendationBuilder);
		topicSubscriber.start();
	}
}