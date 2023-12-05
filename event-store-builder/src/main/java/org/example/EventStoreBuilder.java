package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class EventStoreBuilder {

	private Connection connection;
	private Session session;
	private String brokerURL;
	private String topicName;
	private String clientId;
	private String subscriptionName;

	public EventStoreBuilder(String brokerURL, String topicName, String clientId, String subscriptionName) {
		this.brokerURL = brokerURL;
		this.topicName = topicName;
		this.clientId = clientId;
		this.subscriptionName = subscriptionName;
	}

	public void eventStore(){
		try {
			// Create a connection with a unique client ID
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
			connection = connectionFactory.createConnection();
			connection.setClientID(clientId);
			connection.start();

			// Create a session with CLIENT_ACKNOWLEDGE mode
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

			// Create a destination (topic)
			Topic topic = session.createTopic(topicName);

			// Create a durable subscriber
			TopicSubscriber subscriber = session.createDurableSubscriber(topic, subscriptionName);

			// Implement the MessageListener interface
			MessageListener messageListener = message -> {
				try {
					if (message instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) message;
						String eventData = textMessage.getText();
						System.out.println("Received event: " + eventData);

						// Acknowledge the message
						message.acknowledge();
					}
				} catch (JMSException e) {
					e.printStackTrace();
				}
			};

			// Set the listener to the subscriber
			subscriber.setMessageListener(messageListener);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		String brokerURL = "tcp://localhost:61616";
		String topicName = "YourTopicName";
		String clientId = "YourClientId";
		String subscriptionName = "YourSubscriptionName";
		EventStoreBuilder eventStoreBuilder = new EventStoreBuilder(brokerURL, topicName, clientId, subscriptionName);
		eventStoreBuilder.eventStore();
	}

}
