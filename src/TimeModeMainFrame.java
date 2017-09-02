import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimeModeMainFrame extends JFrame {
	MenuView mv;
	int ROWS = 10;
	int COLS = 10;
	JPanel l1,l2,l3,p1,p2;
	JLabel scoreLabel,timerLabel;
	JButton stopButton;
	JButton buttons[] = new JButton[ROWS*COLS];
	int imageTag[];
	int score = 0;
	JLabel label;
	int popedNum = 1;
	JLabel tipLabel;
	int imageName;
	int time = 10;
	TimeModeMainFrame(MenuView mv) {
		this.mv = mv;
		init();
	}
	
	public void init() {
		this.setSize(460,680); //600 680
		this.setLocationRelativeTo(null);
		p1 = new JPanel();
		p2 = new JPanel();
		l1 = new JPanel();
		l2 = new JPanel();
		l3 = new JPanel();
		l1.setLayout(null);
		l2.setLayout(null);
		l3.setLayout(null);
		p1.setLayout(new GridLayout(3, 1,0,13));
		scoreLabel = new JLabel("分数："+score);
		stopButton = new JButton();
		tipLabel = new JLabel();
		tipLabel.setBounds(165,0,200,30);
		tipLabel.setFont(new Font("",0,20));
		timerLabel = new JLabel(""+time);
		new Thread(new TimerThread(this)).start();
		timerLabel.setHorizontalAlignment(JLabel.CENTER);
		timerLabel.setBounds(100,0,200,30);
		timerLabel.setFont(new Font("",0,20));
		timerLabel.setForeground(Color.red);
		stopButton.setSize(35,35);
		stopButton.setBorderPainted(false);
		stopButton.setContentAreaFilled(false);
		stopButton.setFocusPainted(false);
		setIcon("icons/btn_web_pause_pressed.png",stopButton);
		stopButton.addActionListener(new StopButtonListener());
		
		l1.add(scoreLabel);
		scoreLabel.setBounds(150,0,200,30);
		scoreLabel.setFont(new Font("",0,24));
		
		l1.add(stopButton);
		stopButton.setLocation(20,0);
		
		l2.add(timerLabel);
		l3.add(tipLabel);
		p1.add(l1);
		p1.add(l2);
		p1.add(l3);
		p1.setBounds(0,0,this.getWidth()-5,120);
		p2.setBounds(0,150,this.getWidth()-10,this.getHeight()-180);
		p2.setLayout(new GridLayout(ROWS,COLS,5,5));
		p2.setBackground(Color.LIGHT_GRAY);
		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				buttons[i*COLS+j] = new JButton();
				p2.add(buttons[i*COLS+j]);
				buttons[i*COLS+j].addActionListener(new TimeModeButtonListener(this,buttons,i,j,ROWS,COLS));
				buttons[i*COLS+j].setActionCommand(""+(i*COLS+j));
			}
		}
		
		this.add(p1);
		this.add(p2);
		this.setLayout(null);
		p1.setSize(this.getWidth(),100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		initButtons();
		
		this.setIconImage(new ImageIcon("icons/pass_1.png").getImage());
	}
	
	class StopButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new TimeModePauseDialog(mv,TimeModeMainFrame.this,buttons);
		}
		
	}
	public void judge() {
		if(time<=0) {
			gameover();
		}
	}
	
	public void gameover() {
		for(int i=0;i<buttons.length;i++) {
			//buttons[i].setVisible(false);
			buttons[i].setEnabled(false);
		}
		label = new JLabel();
		label.setSize(this.getWidth(),this.getHeight());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(new Font("", Font.PLAIN, 28));
		JButton backButton = new JButton("返回");
		backButton.setBounds(180,400,100,30);
		backButton.setBackground(new Color(251,216,96));
		backButton.setFont(new Font("",Font.PLAIN,20));
		backButton.setFocusable(false);
		backButton.setBorderPainted(false);
		backButton.addActionListener(new BackButtonListener());
		label.setLayout(null);
		label.add(backButton);
		//label.setText("游戏结束,点我继续");
		File file = new File("time-mode-highest-score.log");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		label.setText("<html><body>"+"最高分:"+highestScore+"<br>"+"得分："+score+"</body></html>");
		this.getLayeredPane().add(label);
		file = new File("sounds/gameover.wav");
		try {
			AudioClip sound = Applet.newAudioClip(file.toURL());
			sound.play();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		//write the highest score into the file
		if(score>highestScore) {
			try {
				FileOutputStream fos = new FileOutputStream("time-mode-highest-score.log");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(new Integer(score));
				oos.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}
	public class BackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			TimeModeMainFrame.this.setVisible(false);
			TimeModeMainFrame.this.dispose();
			mv.setVisible(true);
		}
		
	}
	
	public void initImageTag() {
		imageTag = new int[ROWS*COLS];
		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				if(i<5) {
					imageTag[i*COLS+j] = i+1;
				}else {
					imageTag[i*COLS+j] = i+1-5;
				}
			}
		}
		int temp;
		//break the order
		for(int i=0;i<100;i++) {
			int r1 = (int)(Math.random()*ROWS*COLS);
			int r2 = (int)(Math.random()*ROWS*COLS);
			if(r1 != r2) {
				temp = imageTag[r1];
				imageTag[r1] = imageTag[r2];
				imageTag[r2] = temp;
			}
		}
	}
	
	public void initButtons() {
		initImageTag();
		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				setIcon("icons/"+imageTag[i*COLS+j]+".png",buttons[i*COLS+j]);
				buttons[i*COLS+j].setBorderPainted(false);//去边框
				buttons[i*COLS+j].setFocusPainted(false);
				buttons[i*COLS+j].setContentAreaFilled(false);//设置背景透明
			}
		}
	}
	
	public int getImageTag(int index) {
		return imageTag[index];
	}
	
	public void setImageTag(int index,int value) {
		this.imageTag[index] = value;
	}
	
	public void setScore(int score) {
		this.score = score;
		this.scoreLabel.setText("分数："+this.score);
	}
	public void updateScore() {
		if(popedNum>1) {
			this.score += popedNum*popedNum*5;
			this.scoreLabel.setText("分数："+score);
		}
	}
	
	public int[] getImageTagAll() {
		return imageTag;
	}
	
	public void setImageTagAll(int[] imageTag) {
		this.imageTag = imageTag;
	}
	
	public void setButtons(boolean isVisible[]) {
		for(int i=0;i<buttons.length;i++) {
			buttons[i].setVisible(isVisible[i]); 
			setIcon("icons/"+imageTag[i]+".png",buttons[i]);
		}
	}
	
	//图片自适应按钮按钮大小
	public void setIcon(String file, JButton iconButton) {
			ImageIcon icon = new ImageIcon(file);
			Image temp = icon.getImage().getScaledInstance(iconButton.getWidth(),
					iconButton.getHeight(), icon.getImage().SCALE_DEFAULT);
			icon = new ImageIcon(temp);
			iconButton.setIcon(icon);
    }
	
	public void setPopedNum(int popedNum) {
		this.popedNum = popedNum;
	}
	
	public void setTipLabel() {
		if(popedNum>1) {
			int delta = popedNum*popedNum*5;
			this.tipLabel.setText(this.popedNum+"连消 "+delta);
			new Thread(new SuspendTipLabel(this)).start();
			
			if(popedNum>=6) {
				File file = new File("sounds/good.wav");
				try {
					AudioClip sound = Applet.newAudioClip(file.toURL());
					sound.play();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				JLabel goodLabel = new JLabel();
				goodLabel.setHorizontalAlignment(JLabel.CENTER);
				goodLabel.setVerticalAlignment(JLabel.CENTER);
				String iconPath = "";
				if(popedNum<=8) {
					iconPath = "icons/combo_good.png";
				}else if(popedNum<=10) {
					iconPath = "icons/combo_cool.png";
				}else if(popedNum<=13) {
					iconPath = "icons/combo_awesome.png";
				}else {
					iconPath = "icons/combo_fantastic.png";
				}
				
				time += popedNum-5;
				timerLabel.setText(""+time);
				goodLabel.setIcon(new ImageIcon(iconPath));
				goodLabel.setBounds(0,0,this.getWidth(),this.getHeight());
				this.getLayeredPane().add(goodLabel,new Integer(Integer.MAX_VALUE));
				new Thread(new SuspendGoodLabel(this, goodLabel)).start();
			}
			
		}
		
	}
	
	class SuspendTipLabel extends SuspendThread {
		public SuspendTipLabel(Object obj) {
			super(obj);
		}
		public void suspendMethod() {
			tipLabel.setText("");
		}
	}
	
	class SuspendGoodLabel extends SuspendThread {
		private JLabel goodLabel;
		public SuspendGoodLabel(Object obj,JLabel goodLabel) {
			super(obj);
			this.goodLabel = goodLabel;
			this.millis = 1000;
		}
		public void suspendMethod() {
			goodLabel.setIcon(null);
		}
		
	}
	
	public void updateTime() {
		time--;
		timerLabel.setText(""+time);
	}
}
