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

public class MainFrame extends JFrame {
	MenuView mv;
	int ROWS = 10;
	int COLS = 10;
	JPanel l1,l2,l3,p1,p2;
	JLabel levelLabel,goalLabel,scoreLabel,passLabel;
	JButton stopButton;
	JButton buttons[] = new JButton[ROWS*COLS];
	int imageTag[];
	int level = 1;
	int goal = 1000; //1000
	int score = 0;
	JLabel label;
	int popedNum = 1;
	JLabel tipLabel;
	boolean gameOver = true;
	int imageName;
	int firstCompleted = 0;
	MainFrame(MenuView mv) {
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
		levelLabel = new JLabel("第"+level+"关");
		goalLabel = new JLabel("目标："+goal);
		scoreLabel = new JLabel("分数："+score);
		scoreLabel.setForeground(Color.RED);
		stopButton = new JButton();
		tipLabel = new JLabel();
		tipLabel.setBounds(165,0,200,30);
		tipLabel.setFont(new Font("",0,20));
		
		passLabel = new JLabel();
		passLabel.setBounds(350,0,80,30);
		passLabel.setHorizontalAlignment(JLabel.CENTER);
		passLabel.setVerticalAlignment(JLabel.CENTER);
		ImageIcon icon = new ImageIcon("icons/stage_clear.png");
		Image temp = icon.getImage().getScaledInstance(passLabel.getWidth(),passLabel.getHeight(),icon.getImage().SCALE_DEFAULT);
		icon = new ImageIcon(temp);
		passLabel.setIcon(icon);
		passLabel.setVisible(false);
		
		stopButton.setSize(35,35);
		stopButton.setBorderPainted(false);
		stopButton.setContentAreaFilled(false);
		stopButton.setFocusPainted(false);
		setIcon("icons/btn_web_pause_pressed.png",stopButton);
		stopButton.addActionListener(new StopButtonListener());
		l1.add(levelLabel);
		levelLabel.setBounds(0,0,100,30);
		levelLabel.setFont(new Font("",0,24));
		
		l1.add(goalLabel);
		goalLabel.setBounds(150,0,200,30);
		goalLabel.setFont(new Font("",0,24));
		
		l2.add(scoreLabel);
		scoreLabel.setBounds(150,0,200,30);
		scoreLabel.setFont(new Font("",0,24));
		
		l2.add(stopButton);
		stopButton.setLocation(20,0);
		
		l3.add(tipLabel);
		l3.add(passLabel);
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
				buttons[i*COLS+j].addActionListener(new ButtonListener(this,buttons,i,j,ROWS,COLS));
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
	
	public class StopButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new PauseDialog(mv,MainFrame.this,buttons);
		}
		
	}
	public void judge() {
		gameOver = true;
		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				search(i, j);
				if(!gameOver)
					break;
			}
		}
		if(gameOver) {
			if(score >= goal) {
				this.success();
			}else {
				this.fail();
			}
		
			//write the highest score into the file
			File file = new File("classic-mode-highest-score.log");
			if(!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
			if(score>highestScore) {
				try {
					FileOutputStream fos = new FileOutputStream("classic-mode-highest-score.log");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(new Integer(score));
					oos.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			passLabel.setVisible(false);
		}
	}
	
	//查询是否还有可以消除的方块
	public void search(int i,int j) {
		if(buttons[i*COLS+j].isVisible()) {
			imageName = this.getImageTag(i*COLS+j);
			searchLeft(i,j);
			searchRight(i,j);
			searchUp(i,j);
			searchDown(i,j);
		}
	}
	public void searchLeft(int i,int j) {
		if(i>=0&&i<=(ROWS-1)&&j>0&&j<=(COLS-1)) {
			if(this.getImageTag(i*COLS+j-1) == imageName) {
				this.gameOver = false;
			}
		}
	}
	public void searchRight(int i,int j) {
		if(i>=0&&i<=(ROWS-1)&&j>=0&&j<(COLS-1)) {
			if(this.getImageTag(i*COLS+j+1) == imageName) {
				this.gameOver = false;
			}
		}
	}
	public void searchUp(int i,int j) {
		if(i>0&&i<=(ROWS-1)&&j>=0&&j<=(COLS-1)) {
			if(this.getImageTag((i-1)*COLS+j) == imageName) {
				this.gameOver = false;
			}
		}
	}
	public void searchDown(int i,int j) {
		if(i>=0&&i<(ROWS-1)&&j>=0&&j<=(COLS-1)) {
			if(this.getImageTag((i+1)*COLS+j) == imageName) {
				this.gameOver = false;
			}
		}
	}
	//通关成功
	public void missionCompleted() {
		if(score>=goal&&firstCompleted==1) {
			label = new JLabel();
			label.setSize(this.getWidth(),this.getHeight());
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);
			ImageIcon icon = new ImageIcon("icons/stage_clear.png");
			Image temp = icon.getImage().getScaledInstance(250,165,icon.getImage().SCALE_DEFAULT);
			icon = new ImageIcon(temp);
			label.setIcon(icon);
			this.getLayeredPane().add(label,new Integer(Integer.MAX_VALUE)); //将通关提示消息放到MainFrame的最上层
			File file = new File("sounds/applause.wav");
			try {
				AudioClip sound = Applet.newAudioClip(file.toURL());
				sound.play();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			new Thread(new SuspendPass(this)).start();
		}
		
	}
	
	class SuspendPass extends SuspendThread {
		public SuspendPass(Object obj) {
			super(obj);
		}
		public void suspendMethod() {
			label.setIcon(null);
			MainFrame.this.remove(label);
			passLabel.setVisible(true);
		}
	}
	
	//通关成功并且没有可以消除的方块
	public void success() {
		int leftNum = 0;
		int rewards = 0;
		for(int i=0;i<buttons.length;i++) {
			if(buttons[i].isVisible()) {
				leftNum++;
			}
			buttons[i].setVisible(false);
		}
		if(leftNum<=10) {
			rewards = 2000-leftNum*leftNum*20;
		}
		label = new JLabel();
		label.setSize(this.getWidth(),this.getHeight());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		this.getLayeredPane().add(label);
		label.setFont(new Font("", Font.PLAIN, 28));
		label.setText("剩余："+leftNum+" 奖励："+rewards);
		this.score += rewards;
		this.updateScore();
		this.firstCompleted = 0;
		new Thread(new SuspendTip(this)).start();
	}
	
	public class SuspendTip extends SuspendThread{
		public SuspendTip(Object obj) {
			super(obj);
			super.millis = 1500;
		}
		public void suspendMethod() {
			level++;
			goal += 1500;// 1、1000 2、2500 3、4500 4、6520
			levelLabel.setText("第"+level+"关");
			goalLabel.setText("目标："+goal);
			scoreLabel.setText("分数："+score);
			label.setText("<html><body>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第"+level+"关<br/>"+"目标分数:"+goal+"</body></html>");
			new Thread(new SuspendInitButtons(MainFrame.this)).start();
			
		}
	}
	
	public class SuspendInitButtons extends SuspendThread {
		public SuspendInitButtons(Object obj) {
			super(obj);
			super.millis = 1500;
		}
		public void suspendMethod() {
			label.setText("");
			MainFrame.this.remove(label);
			initButtons();
			for(int i=0;i<buttons.length;i++) {
				buttons[i].setVisible(true);
			}
			File file = new File("sounds/readygo.wav");
			try {
				AudioClip sound = Applet.newAudioClip(file.toURL());
				sound.play();
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			if(score>=goal) {
				label = new JLabel();
				label.setSize(MainFrame.this.getWidth(),MainFrame.this.getHeight());
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setVerticalAlignment(JLabel.CENTER);
				ImageIcon icon = new ImageIcon("icons/stage_clear.png");
				Image temp = icon.getImage().getScaledInstance(250,165,icon.getImage().SCALE_DEFAULT);
				icon = new ImageIcon(temp);
				label.setIcon(icon);
				MainFrame.this.getLayeredPane().add(label,new Integer(Integer.MAX_VALUE)); //将通关提示消息放到MainFrame的最上层
				new Thread(new SuspendPass(MainFrame.this)).start();
				firstCompleted = 2;
			}
		}
	}
	
	public void fail() {
		this.firstCompleted = 0;
		for(int i=0;i<buttons.length;i++) {
			buttons[i].setVisible(false);
		}
		label = new JLabel();
		label.setSize(this.getWidth(),this.getHeight());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(new Font("", Font.PLAIN, 28));
		label.setText("通关失败,点我继续");
		this.getLayeredPane().add(label);
		label.addMouseListener(new LabelMouseListener());
		File file = new File("sounds/gameover.wav");
		try {
			AudioClip sound = Applet.newAudioClip(file.toURL());
			sound.play();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	class LabelMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			level = 1;
			goal = 1000;
			score = 0;
			levelLabel.setText("第"+level+"关");
			goalLabel.setText("目标："+goal);
			scoreLabel.setText("分数："+score);
			label.setText("");
			label.setVisible(false);
			MainFrame.this.remove(label);
			MainFrame.this.initButtons();
			for(int i=0;i<buttons.length;i++) {
				buttons[i].setVisible(true);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			
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
	
	public void setLevel(int level) {
		this.level = level;
		this.levelLabel.setText("第"+this.level+"关");
	}
	
	public void setGoal(int goal) {
		this.goal = goal;
		this.goalLabel.setText("目标："+this.goal);
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
	
	public void addFirstCompleted() {
		this.firstCompleted++;
	}
}
