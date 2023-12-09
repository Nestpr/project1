package org.example.control;
import com.google.gson.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.model.Weather;
import javax.jms.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;

public class JmsWeatherStore implements WeatherStore {
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	private String brokerURL;
	private String destinationName;
	public JmsWeatherStore(String brokerURL, String destinationName) {
		this.brokerURL = brokerURL;
		this.destinationName = destinationName;
	}
	public void storeWeather(List<Weather> weatherList) {
		for (Weather weather : weatherList) {
			String message = serialize(weather);
			eventProducer();
			sendEvent(message);
			close();
		}
	}
	public String serialize(Weather weather) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Instant.class, new InstantSerializer())
				.create();
		return gson.toJson(weather);
	}

	public void eventProducer(){
		try {
			// Create a connection
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
			connection = connectionFactory.createConnection();
			connection.start();

			// Create a session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create a destination (queue or topic)
			Destination destination = session.createTopic(destinationName); // or createTopic for topics

			// Create a producer
			producer = session.createProducer(destination);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	public void sendEvent(String eventData) {
		try {
			// Create an event message
			TextMessage eventMessage = session.createTextMessage(eventData);

			// Send the event message
			producer.send(eventMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	public void close() {
		try {
			if (producer != null) {
				producer.close();
			}
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
}
class InstantSerializer implements JsonSerializer<Instant>{
	public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext){
		return new JsonPrimitive(instant.toString());
	}
}