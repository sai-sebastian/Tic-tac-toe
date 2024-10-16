package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;

public class TicTacToeGUI extends JFrame {
	private GameScreen game;
	private Minimax minimax;
	private JButton[][] buttons;
	private boolean playerTurn;

	public TicTacToeGUI() {
		game = new GameScreen();
		minimax = new Minimax(game);
		buttons = new JButton[3][3];
		// This is for first move of User
		playerTurn = true;
		setTitle("Tic Tac Toe");
		setLayout(new GridLayout(3, 3));
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeButtons();
		setVisible(true);
	}

	// Initializing of boxes of Tic Tac Toe
	private void initializeButtons() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setFont(new Font("Times New Roman", Font.PLAIN, 60));
				buttons[i][j].setFocusPainted(false);
				buttons[i][j].addActionListener(new ButtonClickListener(i, j));
				// adding buttons
				add(buttons[i][j]);
			}
		}
	}

	// This method is used for make move
	private void makeMove(int row, int col, char player) {
		game.makeMove(row, col, player);
		buttons[row][col].setText(String.valueOf(player));
		// Disabling the already moved button
		buttons[row][col].setEnabled(false);
	}

	// Checking status of the game
	private void checkGameStatus() {
		GameDatabase db = new GameDatabase();
		if (game.isWinner('X')) {
			//Inserting game log to database
			db.insertGame("TicTacToe", "X wins");
			//Printing all logs are stored
			db.selectAll();
			JOptionPane.showMessageDialog(this, "Player X wins!");
			resetGame();			
		} else if (game.isWinner('O')) {
			//Inserting game log to database
			db.insertGame("TicTacToe", "O wins");
			//Printing all logs are stored
			 db.selectAll();
			JOptionPane.showMessageDialog(this, "Player O wins!");
			resetGame();
		} else if (!game.isMovesLeft()) {
			//Inserting game log to database
			db.insertGame("TicTacToe", "It's a draw!");
			//Printing all logs are stored
			 db.selectAll();
			JOptionPane.showMessageDialog(this, "It's a draw!");
			resetGame();
		}
	}

	// This method is used for reset game
	private void resetGame() {
		game = new GameScreen();
		minimax = new Minimax(game);
		playerTurn = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setText("");
				buttons[i][j].setEnabled(true);
			}
		}
	}

	private class ButtonClickListener implements ActionListener {
		private int row, col;

		public ButtonClickListener(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// Checking the user can move or not
			if (playerTurn) {
				makeMove(row, col, 'X');
				playerTurn = false;
				checkGameStatus();
				// Checking user winner or not and is any move left or not and check the user
				// movement or not
				if (!game.isWinner('X') && game.isMovesLeft() && !playerTurn) {
					int[] bestMove = minimax.findBestMove('O');
					makeMove(bestMove[0], bestMove[1], 'O');
					playerTurn = true;
					checkGameStatus();
				}
			}
		}
	}
}
