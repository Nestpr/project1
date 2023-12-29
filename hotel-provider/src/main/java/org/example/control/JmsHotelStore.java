package org.example.control;
import com.google.gson.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.model.Hotel;
import javax.jms.*;
import java.lang.reflect.Type;
import java.time.Instant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

public class JmsHotelStore implements HotelStore{
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	private String brokerURL;
	private String destinationName;
	public JmsHotelStore(String brokerURL, String destinationName) {
		this.brokerURL = brokerURL;
		this.destinationName = destinationName;
	}
	public void storeHotel(List<Hotel> hotels) {
		for (Hotel hotel : hotels) {
			String message = serialize(hotel);
			eventProducer();
			sendEvent(message);
			close();
		}
	}
	public String serialize(Hotel hotel) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Instant.class, new InstantSerializer())
				.create();
		return gson.toJson(hotel);
	}

	public void eventProducer(){
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic(destinationName);
			producer = session.createProducer(destination);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	public void sendEvent(String eventData) {
		try {
			TextMessage eventMessage = session.createTextMessage(eventData);
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
class InstantSerializer implements JsonSerializer<Instant> {
	public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
		return new JsonPrimitive(instant.toString());
	}
}
