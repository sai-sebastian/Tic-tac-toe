package tictactoe;

//In this function implementing minimax Algorithm 
public class Minimax {
	private static final int SIZE = 3;
	private GameScreen game;

	public Minimax(GameScreen game) {
		this.game = game;
	}

	// This function used for the finding best move for machine
	public int[] findBestMove(char player) {
		int bestVal = (player == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int[] bestMove = { -1, -1 };
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				// Checking the given box is empty or not
				if (game.getBoard()[i][j] == ' ') {
					game.makeMove(i, j, player);
					// Calling minimax algorithm function
					int moveVal = minimax01(0, (player == 'X') ? false : true, Integer.MIN_VALUE, Integer.MAX_VALUE);
					game.getBoard()[i][j] = ' ';
					// If return value is greater than best value then move to that particular box.
					// It depends on which player want best move.
					if (player == 'X' && moveVal > bestVal) {
						bestMove[0] = i;
						bestMove[1] = j;
						bestVal = moveVal;
					} else if (player == 'O' && moveVal < bestVal) {
						bestMove[0] = i;
						bestMove[1] = j;
						bestVal = moveVal;
					}
				}
			}
		}
		return bestMove;
	}

	// In this function Minimax Algorithm is used
//	private int minimax(int depth, boolean isMax) {
//		int score = evaluate();
//
//		//printArray();
//		if (score == 10 || score == -10)
//			return score;
//		if (!game.isMovesLeft())
//			return 0;
//
//		if (isMax) {
//			int best = Integer.MIN_VALUE;
//			for (int i = 0; i < SIZE; i++) {
//				for (int j = 0; j < SIZE; j++) {
//					if (game.getBoard()[i][j] == ' ') {
//						game.makeMove(i, j, 'X');
//						
//						best = Math.max(best, minimax(depth + 1, false));
//						game.getBoard()[i][j] = ' ';
//					}
//				}
//
//			}
//			return best;
//		} else {
//			int best = Integer.MAX_VALUE;
//			for (int i = 0; i < SIZE; i++) {
//				for (int j = 0; j < SIZE; j++) {
//					if (game.getBoard()[i][j] == ' ') {
//						game.makeMove(i, j, 'O');
//						best = Math.min(best, minimax(depth + 1, true));
//						game.getBoard()[i][j] = ' ';
//					}
//				}
//			}
//			return best;
//		}
//
//	}

	// In this function Minimax Alpha-Beta Pruning Algorithm is used
	private int minimax01(int depth, boolean isMax, int alpha, int beta) {
		int score = evaluate();
		// If Maximizer or Minimizer has won the game return his/her
		// evaluated score
		if (score == 10 || score == -10)
			return score;
		// If there are no more moves and no winner then
		// it is a tie
		if (!game.isMovesLeft())
			return 0;
		// If this maximizer's move
		if (isMax) {
			int best = Integer.MIN_VALUE;
			// Iterating all cells
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					// Checking the given box is empty or not
					if (game.getBoard()[i][j] == ' ') {
						game.makeMove(i, j, 'X');
						// Call minimax recursively and choose
						// the maximum value
						best = Math.max(best, minimax01(depth + 1, false, alpha, beta));
						alpha = Math.max(alpha, best);
						// Undo the move
						game.getBoard()[i][j] = ' ';
						// Alpha Beta Pruning
						if (alpha >= beta) {
							return best;
						}
					}
				}
			}
			return best;
		} else {
			int best = Integer.MAX_VALUE;
			// Iterating all cells
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					// Checking the given box is empty or not
					if (game.getBoard()[i][j] == ' ') {
						game.makeMove(i, j, 'O');
						// Call minimax recursively and choose
						// the minimum value
						best = Math.min(best, minimax01(depth + 1, true, alpha, beta));
						// Undo the move
						game.getBoard()[i][j] = ' ';
						beta = Math.min(beta, best);
						// Alpha Beta Pruning
						if (beta <= alpha) {
							return best;
						}
					}
				}
			}
			return best;
		}
	}

	//This function is used for the evaluating score of the game
	private int evaluate() {
		char[][] board = game.getBoard();
		for (int row = 0; row < SIZE; row++) {
			if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
				if (board[row][0] == 'X')
					return 10;
				else if (board[row][0] == 'O')
					return -10;
			}
		}

		for (int col = 0; col < SIZE; col++) {
			if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
				if (board[0][col] == 'X')
					return 10;
				else if (board[0][col] == 'O')
					return -10;
			}
		}

		if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			if (board[0][0] == 'X')
				return 10;
			else if (board[0][0] == 'O')
				return -10;
		}

		if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
			if (board[0][2] == 'X')
				return 10;
			else if (board[0][2] == 'O')
				return -10;
		}

		return 0;
	}
}
