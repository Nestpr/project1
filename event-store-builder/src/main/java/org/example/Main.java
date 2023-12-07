package org.example;

public class Main {
	public static void main(String[] args){
		String outputDirectory = args[0];
		String brokerURL = "tcp://localhost:61616";
		String topicName = "YourTopicName";
		String clientId = "YourClientId";
		String subscriptionName = "YourSubscriptionName";
		EventStoreBuilder eventStoreBuilder = new FileEventBuilder(outputDirectory);
		TopicSubscriber topicSubscriber = new TopicSubscriber(brokerURL, topicName, clientId, subscriptionName, eventStoreBuilder);
		topicSubscriber.start();
	}
}
