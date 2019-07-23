package breakingBall;


import javax.swing.JFrame;

public class MainClass {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		gamePlay gp = new gamePlay();
		
		window.setBounds(10,10,700,600);
		window.setVisible(true);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Breaking Ball");
		window.add(gp); // adaug Jpanel in JFrame

		

	}

}
