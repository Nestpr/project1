package org.example;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class FileEventBuilder implements EventStoreBuilder{
	private final String outputDirectory;
	public FileEventBuilder(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}
	public void writeEventToFile(String eventData) {
		try {
			String timestamp = extractTimestampFromEvent(eventData);
			String ss = extractSSFromEvent(eventData);
			String directoryPath = outputDirectory + "/eventstore/datalake/" + ss;
			String fileName = directoryPath + "/" + getYYYYMMDD(timestamp) + ".events";
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			FileWriter fileWriter = new FileWriter(fileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(eventData + "\n");
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String extractTimestampFromEvent(String eventData) {
		String timestamp = null;
		try {
			JSONObject jsonEvent = new JSONObject(eventData);
			if (jsonEvent.has("ts")) {
				timestamp = jsonEvent.getString("ts");
			} else {
				System.err.println("timeInstant field not found in eventData");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return timestamp;
	}
	private String getYYYYMMDD(String timestamp) {
		try {
			String date = timestamp.substring(0, 10).replace("-", "");
			return date;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private String extractSSFromEvent(String eventData) {
		try {
			JSONObject jsonEvent = new JSONObject(eventData);
			if (jsonEvent.has("ss")) {
				return jsonEvent.getString("ss");
			} else {
				System.err.println("'ss' field not found in eventData");
				return null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
