package breakingBall;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class bricksGen {
	public int bricks[][];
	public int brickWidth;
	public int brickHeight;
	public bricksGen(int row, int col)
	{
		bricks = new int[row][col];
		for(int i = 0; i < bricks.length; i++) {
			for(int j = 0;j < bricks[0].length; j++) {
				bricks[i][j] = 1;
			}
		}
		brickWidth = 540/col;
		brickHeight = 150/row;	
	}
	
	public void draw(Graphics2D graph) {
		for(int i = 0; i < bricks.length; i++) {
			for(int j = 0;j < bricks[0].length; j++) {
				if(bricks[i][j] > 0)
				{
					graph.setColor(Color.white);
					graph.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					
					graph.setStroke(new BasicStroke(3));
					graph.setColor(Color.black);
					graph.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
		
	}
	
	public void setBrickVal(int val, int row, int col) {
		bricks[row][col] = val;
	}
}
