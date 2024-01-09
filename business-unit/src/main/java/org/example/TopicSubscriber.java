package org.example;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
public class TopicSubscriber implements Subscriber{
	private Connection connection;
	private Session session;
	private final String brokerURL;
	private final TravelRecommendationBuilder travelRecommendationBuilder;
	private final int number;

	public TopicSubscriber(String brokerURL, TravelRecommendationBuilder travelRecommendationBuilder, int number) {
		this.brokerURL = brokerURL;
		this.travelRecommendationBuilder = travelRecommendationBuilder;
		this.number = number;
	}
	public void start(){
		String clientID = "clientID";
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
			connection = connectionFactory.createConnection();
			connection.setClientID(clientID);
			connection.start();
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Topic topic1 = session.createTopic("prediction.Weather");
			String suscriptionName1 = "subscriptionName1";
			javax.jms.TopicSubscriber subscriber = session.createDurableSubscriber(topic1, suscriptionName1);
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
			Topic topic2 = session.createTopic("prediction.Hotel");
			String suscriptionName2 = "subscriptionName2";
			javax.jms.TopicSubscriber subscriber2 = session.createDurableSubscriber(topic2, suscriptionName2);
			MessageListener messageListener2 = message -> {
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
			subscriber2.setMessageListener(messageListener2);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
