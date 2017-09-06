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
 * ���˵�����ĵľ���ģʽ����ʾģʽ����߷���ʾlabel
 * @author Peter
 *
 */
public class HighestScoreLabel extends JLabel implements Runnable {
	/** ����ģʽ��߷���ʾlabel */
	private JLabel classicModeLabel;
	/** ��ʾģʽ��߷���ʾlabel */
	private JLabel timeModeLabel;
	/** ���˵�����Ŀ�� */
	private int width;
	/** ����ģʽ��߷� */
	private int classicHScore;
	/** ��ʾģʽ��߷� */
	private int timeHScore;
	/**
	 * 
	 * @param width ͨ�����췽����ȡ�����˵�����Ŀ��
	 */
	public HighestScoreLabel(int width) {
		this.width = width;
		init();
	}
	/** ��߷�label�ĳ�ʼ������ */
	public void init() {
		classicModeLabel = new JLabel();
		classicModeLabel.setFont(new Font("",Font.PLAIN,20));
		classicModeLabel.setBounds(120,0,width,30);
		classicModeLabel.setText("������߷֣�"+classicHScore);
		timeModeLabel = new JLabel();
		timeModeLabel.setFont(new Font("",Font.PLAIN,20));
		timeModeLabel.setBounds(120,30,width,30);
		timeModeLabel.setText("��ʱ��߷֣�"+timeHScore);
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
	/** ʵ���첽���¼�����߷֣�����߷�������ʱ���� */
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
	
	/** ���¾���ģʽ����߷� */
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
		classicModeLabel.setText("������߷֣�"+classicHScore);
	}
	
	/** ������ʱģʽ����߷� */
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
		timeModeLabel.setText("��ʱ��߷֣�"+timeHScore);
	}
}
