package org.example;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicSubscriber {
	private Connection connection;
	private Session session;
	private final String brokerURL;
	private final String topicName;
	private final String clientId;
	private final String subscriptionName;
	private final TravelRecommendationBuilder travelRecommendationBuilder;

	public TopicSubscriber(String brokerURL, String topicName, String clientId, String subscriptionName, TravelRecommendationBuilder travelRecommendationBuilder) {
		this.brokerURL = brokerURL;
		this.topicName = topicName;
		this.clientId = clientId;
		this.subscriptionName = subscriptionName;
		this.travelRecommendationBuilder = travelRecommendationBuilder;
	}

	public void start(){
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
			connection = connectionFactory.createConnection();
			connection.setClientID(clientId);
			connection.start();
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Topic topic = session.createTopic(topicName);
			javax.jms.TopicSubscriber subscriber = session.createDurableSubscriber(topic, subscriptionName);
			MessageListener messageListener = message -> {
				try {
					if (message instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) message;
						String eventData = textMessage.getText();
						System.out.println("Received event: " + eventData);
						this.travelRecommendationBuilder.recommendation(eventData);
						message.acknowledge();
					}
				} catch (JMSException e) {
					e.printStackTrace();
				}
			};
			subscriber.setMessageListener(messageListener);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
