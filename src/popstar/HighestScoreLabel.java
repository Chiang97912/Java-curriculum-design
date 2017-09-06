package popstar;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JLabel;
/**
 * 主菜单界面的的经典模式和显示模式的最高分显示label
 * @author Peter
 *
 */
public class HighestScoreLabel extends JLabel implements Runnable {
	/** 经典模式最高分显示label */
	private JLabel classicModeLabel;
	/** 显示模式最高分显示label */
	private JLabel timeModeLabel;
	/** 主菜单界面的宽度 */
	private int width;
	/** 经典模式最高分 */
	private int classicHScore;
	/** 显示模式最高分 */
	private int timeHScore;
	/**
	 * 
	 * @param width 通过构造方法获取的主菜单界面的宽度
	 */
	public HighestScoreLabel(int width) {
		this.width = width;
		init();
	}
	/** 最高分label的初始化函数 */
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
		
		FontClass fontClass = new FontClass("fonts/STCAIYUN.TTF",24f);
		Font font = fontClass.loadFont();
		classicModeLabel.setFont(font);
		timeModeLabel.setFont(font);
		updateClassicScore();
		updateTimeScore();
	}
	/** 实现异步更新加载最高分，是最高分数具有时事性 */
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
	
	/** 更新经典模式的最高分 */
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
	
	/** 更新限时模式的最高分 */
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
