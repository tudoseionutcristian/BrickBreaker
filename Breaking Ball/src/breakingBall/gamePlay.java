package breakingBall;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

// trebuie sa exista JPanel pentru ca la window.add 
//sa fie un JPanel (instanta) sa nu fie eroare
public class gamePlay extends JPanel implements KeyListener, ActionListener{
// KeyListener este pentru a detecta comenzile de la tastatura
// ActionListener pentru a misca bila
	private boolean isGameRunning = false; // jocul nu ruleaza initial
	private int score = 0;
	private int noBricks = 21;
	
	private Timer timer; // pentru a seta cat de repede sa se miste bila
	private int speed = 8;
	
	private int sliderposX = 310;
	private int ballposX = 350;
	private int ballposY = 530;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private bricksGen bricks;
	
	public gamePlay() {
		bricks = new bricksGen(3,7);
		addKeyListener(this); // pt a folosi keyListener
		setFocusable(true);
		setFocusTraversalKeysEnabled(false); // pt a nu putea folosi TAB pt a merge la next item... si restu de combinatii
		timer = new Timer(speed,this);
		timer.start();
	}
	
	public void paint(Graphics graph) {
		//background
		graph.setColor(Color.black);
		graph.fillRect(1,1,692,592);
		
		bricks.draw((Graphics2D)graph);
		
		if(ballposX == 350 && ballposY == 530) {
		graph.setColor(Color.green);
		graph.setFont(new Font("serif", Font.BOLD,25));
		graph.drawString("Press left or right arrow to start",190,300);
		}
		
		//score
		graph.setColor(Color.white);
		graph.setFont(new Font("serif", Font.BOLD,25));
		graph.drawString("" +score,590,30);
		
		// borders
		graph.setColor(Color.red);
		graph.fillRect(0,0,3,592);
		graph.fillRect(0, 0, 692, 3);
		graph.fillRect(691, 0, 3, 592);
		
		//paddle
		graph.setColor(Color.green);
		graph.fillRect(sliderposX, 550, 100, 8);
		
		//ball
		graph.setColor(Color.yellow);
		graph.fillOval(ballposX, ballposY, 20, 20);
		
		if(noBricks <= 0) {
			isGameRunning = false;
			ballXdir = ballYdir = 0;
			graph.setColor(Color.pink);
			graph.setFont(new Font("seif",Font.BOLD,30));
			graph.drawString("You WON !! ",260, 300);
			
			graph.setFont(new Font("seif",Font.BOLD,20));
			graph.drawString("Press Enter to Restart ",230, 350);
		}
		
		if(ballposY > 570) {
			isGameRunning = false;
			ballXdir = ballYdir = 0;
			graph.setColor(Color.pink);
			graph.setFont(new Font("seif",Font.BOLD,30));
			graph.drawString("Game Over, Score: " + score,190, 300);
			
			graph.setFont(new Font("seif",Font.BOLD,20));
			graph.drawString("Press Enter to Restart ",230, 350);
			
			
		}
		
		graph.dispose(); // distruge window-ul si ce este in mem va fi curatat de sistemul de operare
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(isGameRunning) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(sliderposX,550,100,8))) {
				ballYdir = -ballYdir;
			}
			
			A: for(int i = 0; i < bricks.bricks.length; i++) {
				for(int j = 0; j < bricks.bricks[0].length; j++) {
					if(bricks.bricks[i][j] > 0) {
						int brickX = j * bricks.brickWidth + 80;
						int brickY = i * bricks.brickHeight + 50;
						int brickWidth = bricks.brickWidth;
						int brickHeight = bricks.brickHeight;
						
						Rectangle r = new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballr = new Rectangle(ballposX, ballposY,20,20);
						Rectangle brickr = r;
						
						if(ballr.intersects(brickr)) {
							bricks.setBrickVal(0, i, j);
							noBricks--;
							score += 10;
							// pt a muta mingea in directia opusa dupa ce a spart o caramida
							if(ballposX + 19 <= brickr.x || ballposX +1 >= brickr.x + brickr.width) {
								ballXdir = - ballXdir;
							}else {
								ballYdir = -ballYdir;
							}
							break A;
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir; // left border
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) {
				ballXdir = -ballXdir; // dreapta
			}
		}
		repaint(); //reapeleaza metoda paint si va desena din nou dar cu date modificate
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			if(!isGameRunning) {
				isGameRunning = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				sliderposX = 310;
				score = 0;
				noBricks = 21;
				bricks = new bricksGen(3,7);
				
				repaint();
			}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(sliderposX >= 600) {
				sliderposX = 600;
			}else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(sliderposX < 10) {
				sliderposX = 10;
			}else {
				moveLeft();
			}
		}
		
	}
	
	public void moveRight(){
		isGameRunning = true;
		sliderposX += 20; // misca cu 20px dreapta sliderul	
	}
	public void moveLeft(){
		isGameRunning = true;
		sliderposX -= 20; // misca cu 20px stanga sliderul	
	}
	

}
