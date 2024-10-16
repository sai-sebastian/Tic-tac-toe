package tictactoe;

public class GameWindow {
	public static void main(String[] args) throws Exception {
		GameDatabase db = new GameDatabase();
		db.createNewDatabase();
		db.createNewTable();
		// Calling GUI Class
		new TicTacToeGUI();
	}
}
