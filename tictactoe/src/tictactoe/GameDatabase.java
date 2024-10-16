package tictactoe;

import java.sql.*;

//This class is used for saving game log status
public class GameDatabase {
	// This function is used for database connection
	private Connection connect() {
		String url = "jdbc:sqlite:tic_tac_toe.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	// This function is used for creating database
	public void createNewDatabase() {
		String url = "jdbc:sqlite:tic_tac_toe.db";

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	//This function is used for the creating table
	public void createNewTable() {
		String url = "jdbc:sqlite:tic_tac_toe.db";

		String sql = "CREATE TABLE IF NOT EXISTS games (\n" + " id integer PRIMARY KEY,\n" + " board text NOT NULL,\n"
				+ " result text\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	//This function is used for the insert data to that table
	public void insertGame(String board, String result) {
		String sql = "INSERT INTO games(board, result) VALUES(?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, board);
			pstmt.setString(2, result);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	//This function is used for printing game status logs from the database
	public void selectAll() {
		String sql = "SELECT id, board, result FROM games";

		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getString("board") + "\t" + rs.getString("result"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
