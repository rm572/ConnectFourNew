import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    public final int screenWidth = 715;
    public final int screenHeight = 700;


    //Full screen code
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    // FPS
    int FPS = 60;
    int counter = 0;

    KeyHandler keyH = new KeyHandler(this);
    Board board = new Board(this);

    Thread gameThread;

    boolean gameOn = true;
    boolean displayedScore = false;
    String player = "Player";
    public boolean updateTrue = true;
    boolean moved = true;
    public boolean endGame = false;
	public int[] SCOREBOARD = {0, 0};   

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame() {
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        board.draw(g2);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        int[] moves = new int[2];
        if (board.PLAYER == 0) {
            if (keyH.zeroPressed) {
                moves = board.playerMove(0, g2);
                keyH.zeroPressed = false;
            }
            else if (keyH.onePressed) {
                moves = board.playerMove(1, g2);
                keyH.onePressed = false;
            }
            else if (keyH.twoPressed) {
                moves = board.playerMove(2, g2);
                keyH.twoPressed = false;
            }
            else if (keyH.threePressed) {
                moves = board.playerMove(3, g2);
                keyH.threePressed = false;
            }
            else if (keyH.fourPressed) {
                moves = board.playerMove(4, g2);
                keyH.fourPressed = false;
            }
            else if (keyH.fivePressed) {
                moves = board.playerMove(5, g2);
                keyH.fivePressed = false;
            }
            else if (keyH.sixPressed) {
                moves = board.playerMove(6, g2);
                keyH.sixPressed = false;
            }
            if (board.curMove == 1) {
                board.PLAYER = (board.PLAYER + 1) % 2;
                board.TOTAL_MOVES++;
                gameOn = board.contGame2(moves[0], moves[1]);
                board.printBoard();
                moved = true;
            }
        }
        else {
            player = "Computer";
            System.out.println();
            System.out.println(player + " move");
            moves = board.compMove(g2);
            board.printBoard();
            if (board.curMove == 0) {
                board.PLAYER = (board.PLAYER + 1) % 2;
                board.TOTAL_MOVES++;
                gameOn = board.contGame2(moves[0], moves[1]);
                board.printBoard();
                moved = true;
            }
        }
        
        if (!gameOn) {
            System.out.println(player + " wins!");
            SCOREBOARD[board.PLAYER]++;
        }
        else if (board.TOTAL_MOVES == 42) {
            System.out.println("It's a draw!");
            gameOn = false;
        }
        
    }

    @Override
    public void run() {
        board.draw(g2);
        while (gameThread != null) {
            if (gameOn) {
                update();
                repaint();
            }
            else {
                if (!displayedScore) {
                    displayedScore = true;
                    System.out.println(SCOREBOARD[1] + " : " + SCOREBOARD[0]);
                }
                
                if (keyH.rPressed) {
                    keyH.rPressed = false;
                    displayedScore = false;
                    board.resetBoard(g2);
                    repaint();
                    gameOn = true;
                    
                }
            }
        }
    }
}
