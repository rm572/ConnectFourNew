import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Board {

    public String[][] BOARD;
    public String[][] tempBoard;
    public int[] COLS;
    public int TOTAL_MOVES = 0;
    public boolean noBest = false;
    public int LENGTH;
	public int PLAYER = 0;
	public int DIFF;
	public int HEIGHT;
	public int POS;
	public int LW = 15;
    public GamePanel gp;
    int[] move = new int[2];
    Graphics2D g2;
    int curMove = 0;
    boolean moveCompleted = false;

    public Board(GamePanel gp) {
        this.gp = gp;
        BOARD = new String[][]{{" ", " ", " ", " ", " ", " ", " "}, 
        {" ", " ", " ", " ", " ", " ", " "}, 
        {" ", " ", " ", " ", " ", " ", " "}, 
        {" ", " ", " ", " ", " ", " ", " "}, 
        {" ", " ", " ", " ", " ", " ", " "}, 
        {" ", " ", " ", " ", " ", " ", " "}};
        tempBoard = new String[][]{{" ", " ", " ", " ", " ", " ", " "}, 
        {" ", " ", " ", " ", " ", " ", " "}, 
        {" ", " ", " ", " ", " ", " ", " "}, 
        {" ", " ", " ", " ", " ", " ", " "}, 
        {" ", " ", " ", " ", " ", " ", " "}, 
        {" ", " ", " ", " ", " ", " ", " "}};

        COLS = new int[]{0, 0, 0, 0, 0, 0, 0};
        TOTAL_MOVES = 0;
        LENGTH = gp.screenWidth;
        DIFF = gp.screenHeight - LENGTH;
        HEIGHT = gp.screenHeight;
        POS = (LENGTH)/7;

    }
    public void printBoard() {
		for (int r = 5; r >= 0; r--) {
			for (int c = 0; c < 7; c++) {
				System.out.print("|" + BOARD[r][c]);
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println(" 0 1 2 3 4 5 6");
	}

    public void resetBoard(Graphics2D g2) {
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 7; c++) {
				BOARD[r][c] = " ";
                tempBoard[r][c] = " ";
                COLS[c] = 0;
			}
		}
        TOTAL_MOVES = 0;	
        PLAYER = 0;
        curMove = 0;
	}

	public void write() {
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 		
		g2.setColor(Color.BLUE);
		g2.drawString(" 0      1      2      3      4      5      6", 30, 675);

	}

    public void drawMove(Graphics2D g2, Color color, int col, int row) {
        g2.setColor(color);
		g2.fillOval(LW + POS * col, 540-(LW + row * POS), POS-LW, POS-LW);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        // System.out.println("Draw");
		g2.setColor(Color.BLACK);
		g2.fillRect(0, DIFF, LW, 630);
		for (int c = 1; c <= 7; c++) {
			// vertical
			g2.fillRect(c * POS, DIFF, LW, 630);

			//horizontal
			if (c <= 7) {
				g2.fillRect(0, (c-1) * POS, LENGTH, LW);
			}
		}

        for (int r = 0; r < tempBoard.length; r++) {
            for (int c = 0; c < tempBoard[r].length; c++) {
                if (tempBoard[r][c].equals("X")) {
                    drawMove(g2, Color.RED, c, r);
                }
                else if (tempBoard[r][c].equals("O")) {
                    drawMove(g2, Color.GREEN, c, r);
                }
            }
        }

        write();
        updateScore(g2);
        
        if (!gp.gameOn) {
            
            writeInstructions();
        }
        
    }

    public void writeInstructions() {
		g2.setFont(new Font("TimesRoman", Font.BOLD, 75));		
		g2.setColor(Color.BLUE);
		g2.drawString("Press R to Restart", 80, 300);
    }
	
	public void updateScore(Graphics2D g2) {
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
		g2.setColor(Color.GREEN);
		g2.drawString(gp.SCOREBOARD[1] + " ", LENGTH/2 - 50, 50);
		
		g2.setColor(Color.BLACK);
		g2.drawString(":", LENGTH/2, 50);
		
		g2.setColor(Color.RED);
		g2.drawString(" " + gp.SCOREBOARD[0], LENGTH/2 + 30, 50);
	}

    public int[] playerMove(int col, Graphics2D g2) {
        int row = COLS[col];
        COLS[col] = row + 1;
		BOARD[row][col] = "O";
        System.out.println("Player");
        curMove = 1;
        tempBoard[row][col] = "O";
		return new int[]{row, col};
	}



    public boolean contGame2(int row, int col) {
		//Vertical
		if (row >= 3 && BOARD[row][col].equals(BOARD[row-1][col]) && BOARD[row-1][col].equals(BOARD[row-2][col]) && BOARD[row-2][col].equals(BOARD[row-3][col])) {
			return false;
		}

		//Horizontal
		for (int c = 0; c < 4; c++) {
			if (!BOARD[row][c].equals(" ") && BOARD[row][c].equals(BOARD[row][c+1]) && BOARD[row][c].equals(BOARD[row][c+2]) && BOARD[row][c].equals(BOARD[row][c+3])) {
				return false;
			}
		}

		

		for (int r = 0; r < 6 - 3; r++) {
            for (int c = 0; c < 7 - 3; c++) {
                if (!BOARD[r][c].equals(" ") && BOARD[r][c].equals(BOARD[r + 1][c + 1]) && BOARD[r + 1][c + 1].equals(BOARD[r + 2][c + 2]) && BOARD[r + 2][c + 2].equals(BOARD[r + 3][c + 3])) {
                    return false;
                }
            }
        }
        for (int r = 3; r < 6; r++) {
            for (int c = 0; c < 7 - 3; c++) {
                if (!BOARD[r][c].equals(" ") && BOARD[r][c].equals(BOARD[r - 1][c + 1]) && BOARD[r - 1][c + 1].equals(BOARD[r - 2][c + 2]) && BOARD[r - 2][c + 2].equals(BOARD[r - 3][c + 3])) {
                    return false;
                }
            }
        }



		return true;
	}

    public int[] compMove(Graphics2D g2) {
        // this.g2 = g2;
		int row;
		int col;
		System.out.println("comp");
		
		if (TOTAL_MOVES == 1) {
			if(COLS[2] == 0) {
				row = 0;
				col = 2;
			}
			else {
				row = 0;
				col = 3;
			}
			COLS[col] = 1;
		}
		else {
			int[] temp = findBestMove();
			row = temp[0];
			col = temp[1];
		}
		BOARD[row][col] = "X";
        curMove = 0;
        tempBoard[row][col] = "X";
		return new int[]{row, col};
	}
	

	public boolean isMoveLegal(int c) {
		try { 
            int r = COLS[c];
            // System.out.println("r is: " + r);
			if ((0 <= c && c <= 6) && (0 <= r && r <= 5)) {
				return true;
			}
			return false;
		}
		catch (Exception e) {
			return false;
		}

	}

	public boolean isMovesLeft() {
	    for (int r = 0; r < 3; r++) {
	        for (int c = 0; c < 3; c++) {
	            if (BOARD[r][c].equals(" ")) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

	public int chooseCOL() {
		if (COLS[2] <= COLS[3] && COLS[2] <= COLS[4] && isMoveLegal(2)) {
			return 2;
		}

		else if (COLS[3] <= COLS[2] && COLS[3] <= COLS[4] && isMoveLegal(3)) {
			return 3;
		}

		else if (COLS[4] <= COLS[2] && COLS[4] <= COLS[3] && isMoveLegal(4)) {
			return 4;
		}
		else {
			if (isMoveLegal(2)) {
				return 2;
			}
			else if (isMoveLegal(4)) {
				return 4;
			}
			else if (isMoveLegal(3)) {
				return 3;
			}
			else if (isMoveLegal(1)) {
				return 1;
			}
			else if (isMoveLegal(5)) {
				return 5;
			}
			else if (isMoveLegal(0)) {
				return 0;
			}
			else {
				return 6;
			}
		}		



	}

	public int evaluate(boolean g) {
		if (g) {
			return 0;
		}

		else {
			return (PLAYER == 1) ? 20 : -20;
		}
	}

	// public static int minimax(boolean isComp, int depth)	{
	public int minimax(boolean isComp, int depth, int row, int col) {
		// int score = evaluate(contGame());
		int score = evaluate(contGame2(row, col));

		if (score == 20) {
			return score - depth;
		}
		else if (score == -20) {
			return score + depth;
		}
		if (depth == 6) {
			noBest = true;
			return 0;
		}
		if (isMovesLeft() == false) {
			return 0;
		}
		
		if (isComp) {
			int best = Integer.MIN_VALUE;

            for (int c = 0; c < 7; c++) {
                if (isMoveLegal(c)) {
                    int r = COLS[c];
                    COLS[c] = r + 1;
                    BOARD[r][c] = "X";
                    
                    best = Math.max(best, minimax(false, depth + 1, r, c));
                    BOARD[r][c] = " ";
                    COLS[c] = COLS[c] - 1;
                }
            }
			return best;
		}
		
		else {
			int best = Integer.MAX_VALUE;
			
            for (int c = 0; c < 7; c++) {
                if (isMoveLegal(c)) {
                    int r = COLS[c];
                    COLS[c] = r + 1;
                    BOARD[r][c] = "O";
                    best = Math.min(best, minimax(true, depth + 1, r, c));
                    BOARD[r][c] = " ";
                    COLS[c] = COLS[c]-1;
                }
            }
			return best;
		}
	}
	
	
	public int[] findBestMove()	{
		int bestVal = -1000;
		int[] bestMove = new int[2];
		int col = -1;
		int row = -1;
		boolean n = false;

        for (int c = 0; c < 7; c++){
            if (isMoveLegal(c)) {
                int r = COLS[c];
                COLS[c] = r + 1;
                BOARD[r][c] = "X";
                if (contGame2(r, c) == false) {
                    return new int[]{r, c};
                }
                BOARD[r][c] = "O";
				// BOARD[r][c] = " ";
                // BOARD[r][c] = "O";
				

                int moveVal = minimax(true, 0, r, c);
        
                BOARD[r][c] = " ";
                COLS[c] = COLS[c] - 1;
        
                if (moveVal > bestVal) {
                    col = c;
                    row = r;
                    bestVal = moveVal;
                    n = noBest;
                }
				noBest = false;
            }
        }
		if (!n) {
			bestMove[0] = row;
			bestMove[1] = col;
			COLS[col] = COLS[col] + 1;
		}
		else {
			int c = chooseCOL();
			int r = COLS[c];
			COLS[c] = r + 1;
			bestMove[0] = r;
			bestMove[1] = c;
		}
		noBest = false;
		return bestMove;
	}
}
