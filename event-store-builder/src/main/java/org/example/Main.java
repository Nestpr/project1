package org.example;
public class Main {
	public static void main(String[] args){
		String outputDirectory = args[0];
		String brokerURL = args[1];
		String topicName = args[2];
		String clientId = args[3];
		String subscriptionName = args[4];
		EventStoreBuilder eventStoreBuilder = new FileEventBuilder(outputDirectory);
		TopicSubscriber topicSubscriber = new TopicSubscriber(brokerURL, topicName, clientId, subscriptionName, eventStoreBuilder);
		topicSubscriber.start();
	}
}
