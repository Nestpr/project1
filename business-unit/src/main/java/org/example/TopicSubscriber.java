package org.example;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TopicSubscriber implements Subscriber {
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

	public void start() {
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
						LocalDate fechaActual = LocalDate.now();
						int diaActual = fechaActual.getDayOfMonth();
						ObjectMapper objectMapper = new ObjectMapper();
						JsonNode jsonNode = objectMapper.readTree(eventData);
						String predictionTimeString = jsonNode.path("predictionTime").asText();
						LocalDateTime predictionTime = LocalDateTime.parse(predictionTimeString, DateTimeFormatter.ISO_DATE_TIME);
						int diaDePredictionTime = predictionTime.getDayOfMonth();
						int diafinal = diaActual + number;
						if (diaActual <= diaDePredictionTime && diaDePredictionTime <= diafinal) {
							System.out.println("Received event: " + eventData);
							this.travelRecommendationBuilder.recommendation(eventData);
							message.acknowledge();
						}
					}
				} catch (JMSException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
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
					throw new RuntimeException(e);
				}
			};
			subscriber2.setMessageListener(messageListener2);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
