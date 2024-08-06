import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    public boolean zeroPressed, onePressed, twoPressed, threePressed, fourPressed, fivePressed, sixPressed, rPressed;

    boolean playerMoved = false;
    int move;


    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
    
            if (code == KeyEvent.VK_0) {
                if (gp.board.isMoveLegal(0)) {
                    zeroPressed = true;
                }
                
            }
            else if (code == KeyEvent.VK_1) {
                if (gp.board.isMoveLegal(1)) {
                    onePressed = true;
                }
            }
            else if (code == KeyEvent.VK_2) {
                if (gp.board.isMoveLegal(2)) {
                    twoPressed = true;
                }
            }
            else if (code == KeyEvent.VK_3) {
                if (gp.board.isMoveLegal(3)) {
                    threePressed = true;
                }
            }
            else if (code == KeyEvent.VK_4) {
                if (gp.board.isMoveLegal(4)) {
                    fourPressed = true;
                }
            }
            else if (code == KeyEvent.VK_5) {
                if (gp.board.isMoveLegal(5)) {
                    fivePressed = true;
                }
    
            }
            else if (code == KeyEvent.VK_6) {
                if (gp.board.isMoveLegal(6)) {
                    sixPressed = true;
                }
            }
            else if (code == KeyEvent.VK_R) {
                rPressed = true;
            }
    }

   
    @Override
    public void keyReleased(KeyEvent e) {
       
    }
       
}
