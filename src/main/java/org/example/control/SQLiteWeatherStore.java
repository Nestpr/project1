package org.example.control;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class SQLiteWeatherStore {
	public static Connection connect(String dbPath) {
		Connection conn = null;
		try {
			String url = "jdbc:sqlite:" + dbPath;
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
			return conn;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	private static void createTable(Statement statement) throws SQLException {
		statement.execute("CREATE TABLE IF NOT EXISTS weather (" +
				"timeInstant TEXT,\n" +
				"rain REAL,\n" +
				"wind REAL,\n" +
				"temperature REAL,\n" +
				"humidity REAL,\n"+
				");");
	}

	private static boolean insert(Statement statement) throws SQLException {
		return statement.execute("INSERT INTO products (id,name ,prize)\n" +
				"VALUES(1, 'hibike', 1995.9),(2, 'orbea', 995.5);");
	}






}
