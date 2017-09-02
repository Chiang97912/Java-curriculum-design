import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JLabel;

public class HighestScoreLabel extends JLabel implements Runnable {
	private JLabel classicModeLabel;
	private JLabel timeModeLabel;
	private int width;
	private int classicHScore;
	private int timeHScore;
	public HighestScoreLabel(int width) {
		this.width = width;
		init();
	}
	public void init() {
		classicModeLabel = new JLabel();
		classicModeLabel.setFont(new Font("",Font.PLAIN,20));
		classicModeLabel.setBounds(120,0,width,30);
		classicModeLabel.setText("经典最高分："+classicHScore);
		timeModeLabel = new JLabel();
		timeModeLabel.setFont(new Font("",Font.PLAIN,20));
		timeModeLabel.setBounds(120,30,width,30);
		timeModeLabel.setText("限时最高分："+timeHScore);
		this.setLayout(null);
		this.add(classicModeLabel);
		this.add(timeModeLabel);
		this.setBounds(0, 0, width, 80 );
		
		updateClassicScore();
		updateTimeScore();
	}
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			updateClassicScore();
			updateTimeScore();
		}
	}
	
	public void updateClassicScore() {
		//read the highest score from the file
		File file = new File("classic-mode-highest-score.log");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(file.length()>0) {
			int highestScore = 0;
			try {
				FileInputStream fis = new FileInputStream("classic-mode-highest-score.log");
				ObjectInputStream ois = new ObjectInputStream(fis);
				highestScore = ((Integer)ois.readObject()).intValue();
				ois.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			classicHScore = highestScore;
		}
		classicModeLabel.setText("经典最高分："+classicHScore);
	}
	
	public void updateTimeScore() {
		//read the highest score from the file
		File file = new File("time-mode-highest-score.log");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(file.length()>0) {
			int highestScore = 0;
			try {
				FileInputStream fis = new FileInputStream("time-mode-highest-score.log");
				ObjectInputStream ois = new ObjectInputStream(fis);
				highestScore = ((Integer)ois.readObject()).intValue();
				ois.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			timeHScore = highestScore;
		}
		timeModeLabel.setText("限时最高分："+timeHScore);
	}
}
