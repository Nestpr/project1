package org.example;
public class Main {
	public static void main(String[] args){
		String outputDirectory = args[0];
		String brokerURL = args[1];
		EventStoreBuilder eventStoreBuilder = new FileEventBuilder(outputDirectory);
		TopicSubscriber topicSubscriber = new TopicSubscriber(brokerURL, eventStoreBuilder);
		topicSubscriber.start();
	}
}
