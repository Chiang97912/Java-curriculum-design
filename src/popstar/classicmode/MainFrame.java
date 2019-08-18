package popstar.classicmode;
import popstar.*;
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
/**
 * ����ģʽ��������
 * @author Peter
 *
 */
public class MainFrame extends JFrame {
	/** �˵�������ʵ�� */
	MenuView mv;
	/** ��ť���� */
	int ROWS = 10;
	/** ��ť���� */
	int COLS = 10;
	/** �������� */
	JPanel l1,l2,l3,p1,p2;
	/**  */
	JLabel levelLabel,goalLabel,scoreLabel,passLabel;
	/** ��Ϸ��ͣ��ť */
	JButton stopButton;
	/** ��Ϸͼ�갴ť */
	JButton buttons[] = new JButton[ROWS*COLS];
	/** ��ťͼ�� */
	int imageTag[];
	/** ��Ϸ�ȼ� */
	int level = 1;
	/** Ŀ����� */
	int goal = 1000; //1000
	/** ��ǰ��÷��� */
	int score = 0;
	JLabel label;
	/** ���ͼ��һ��������ͼ������ */
	int popedNum = 1;
	/** ����������ʾ��ǩ */
	JLabel tipLabel;
	/** ���ֵΪtrue���ʾ��Ϸ������Ϊfalse��ʾ��Ϸ��δ���� */
	boolean gameOver = true;
	int imageName;
	/** �ж���Ϸ�Ƿ��ǵ�һ�δﵽ��ǰ��������Ŀ����� */
	int firstCompleted = 0;
	
	/**
	 * ���˵������ʵ������
	 * @param mv �˵�������Ķ���
	 */
	public MainFrame(MenuView mv) {
		this.mv = mv;
		init();
	}
	
	/**
	 * ��Ϸ������ĳ�ʼ������
	 */
	public void init() {
		FontClass fontClass = new FontClass("fonts/STCAIYUN.TTF",28f);
		Font font = fontClass.loadFont();
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
		p1.setLayout(new GridLayout(3, 1,0,0));
		levelLabel = new JLabel("��"+level+"��");
		goalLabel = new JLabel("Ŀ�꣺"+goal);
		scoreLabel = new JLabel("������"+score);
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
		levelLabel.setBounds(0,0,100,45);
		levelLabel.setFont(new Font("",0,24));
		
		l1.add(goalLabel);
		goalLabel.setBounds(150,0,200,45);
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
		p1.setBounds(0,0,this.getWidth()-5,120); //120
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
		p1.setSize(this.getWidth(),150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		initButtons();
		
		this.setIconImage(new ImageIcon("icons/pass_1.png").getImage());
		
		levelLabel.setFont(font);
		goalLabel.setFont(font);
		goalLabel.setForeground(Color.orange);
		scoreLabel.setFont(font);
		tipLabel.setFont(font);
		tipLabel.setForeground(Color.GREEN);
		this.setTitle("����������-����ģʽ");
	}
	/** ��ͣ��Ϸ��ť������ */
	public class StopButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new PauseDialog(mv,MainFrame.this,buttons);
		}
		
	}
	/** �ж���Ϸ�еķ����Ƿ������� */
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
			if(file.length()>0) {
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
	
	/** ��ѯ�Ƿ��п��������ķ��� */
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
	/** ��ǰ��������Ŀ��������ﵽͨ������ִ�иú��� */
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
			this.getLayeredPane().add(label,new Integer(Integer.MAX_VALUE)); //��ͨ����ʾ��Ϣ�ŵ�MainFrame�����ϲ�
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
	/** ����������ͨ����Ϣ��ʾһ��ʱ��������Ϸ�������Ƴ� */
	public class SuspendPass extends SuspendThread {
		public SuspendPass(Object obj) {
			super(obj);
		}
		public void suspendMethod() {
			label.setIcon(null);
			MainFrame.this.remove(label);
			passLabel.setVisible(true);
		}
	}
	
	/** ͨ�سɹ�����û�п��������ķ���ִ�иú��� */
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
		label.setText("ʣ�ࣺ"+leftNum+" ������"+rewards);
		this.score += rewards;
		this.updateScore();
		this.firstCompleted = 0;
		new Thread(new SuspendTip(this)).start();
	}
	
	/** ������һ�ص������ʾ��Ϣ */
	public class SuspendTip extends SuspendThread{
		public SuspendTip(Object obj) {
			super(obj);
			super.millis = 1500;
		}
		public void suspendMethod() {
			level++;
			goal += 1500;// 1��1000 2��2500 3��4500 4��6520
			levelLabel.setText("��"+level+"��");
			goalLabel.setText("Ŀ�꣺"+goal);
			scoreLabel.setText("������"+score);
			label.setText("<html><body>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��"+level+"��<br/>"+"Ŀ�����:"+goal+"</body></html>");
			new Thread(new SuspendInitButtons(MainFrame.this)).start();
			
		}
	}
	
	/** ��ʼ����һ�صķ���ͼ��͸�����Ϸ�ȼ���Ŀ����� */
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
				MainFrame.this.getLayeredPane().add(label,new Integer(Integer.MAX_VALUE)); //��ͨ����ʾ��Ϣ�ŵ�MainFrame�����ϲ�
				new Thread(new SuspendPass(MainFrame.this)).start();
				firstCompleted = 2;
			}
		}
	}
	
	/** û�п����������һ�÷���С��Ŀ�����ִ�и÷��� */
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
		label.setText("ͨ��ʧ��,���Ҽ���");
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
	/** ��ʼ����Ϸ���浽��һ�� */
	class LabelMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			level = 1;
			goal = 1000;
			score = 0;
			levelLabel.setText("��"+level+"��");
			goalLabel.setText("Ŀ�꣺"+goal);
			scoreLabel.setText("������"+score);
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
	
	/** ��ʼ��imageTag���� */
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
	
	/** ��ʼ��buttons */
	public void initButtons() {
		initImageTag();
		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				setIcon("icons/"+imageTag[i*COLS+j]+".png",buttons[i*COLS+j]);
				buttons[i*COLS+j].setBorderPainted(false);//ȥ�߿�
				buttons[i*COLS+j].setFocusPainted(false);
				buttons[i*COLS+j].setContentAreaFilled(false);//���ñ���͸��
			}
		}
	}
	
	/** ��ȡָ������ֵ��imageTagֵ */
	public int getImageTag(int index) {
		return imageTag[index];
	}
	/** ����ָ������ֵ��imageTagֵΪָ��ֵvalue */
	public void setImageTag(int index,int value) {
		this.imageTag[index] = value;
	}
	/** ������Ϸ�ȼ� */
	public void setLevel(int level) {
		this.level = level;
		this.levelLabel.setText("��"+this.level+"��");
	}
	
	/** ������ϷĿ����� */
	public void setGoal(int goal) {
		this.goal = goal;
		this.goalLabel.setText("Ŀ�꣺"+this.goal);
	}
	
	/** ������Ϸ���� */
	public void setScore(int score) {
		this.score = score;
		this.scoreLabel.setText("������"+this.score);
	}
	
	/** ������Ϸ���� */
	public void updateScore() {
		if(popedNum>1) {
			this.score += popedNum*popedNum*5;
			this.scoreLabel.setText("������"+score);
		}
	}
	
	/** ����imageTagָ�� */
	public int[] getImageTagAll() {
		return imageTag;
	}
	
	/** ����imageTagֵ */
	public void setImageTagAll(int[] imageTag) {
		this.imageTag = imageTag;
	}
	/** ����buttons�а�ť�Ƿ�ɼ� */
	public void setButtons(boolean isVisible[]) {
		for(int i=0;i<buttons.length;i++) {
			buttons[i].setVisible(isVisible[i]); 
			setIcon("icons/"+imageTag[i]+".png",buttons[i]);
		}
	}
	
	/** ʹͼƬ����Ӧ��ť��ť��С */
	public void setIcon(String file, JButton iconButton) {
			ImageIcon icon = new ImageIcon(file);
			Image temp = icon.getImage().getScaledInstance(iconButton.getWidth(),
					iconButton.getHeight(), icon.getImage().SCALE_DEFAULT);
			icon = new ImageIcon(temp);
			iconButton.setIcon(icon);
    }
	
	/** ����popedNumֵ */
	public void setPopedNum(int popedNum) {
		this.popedNum = popedNum;
	}
	
	/** ����tipLabel��textֵ */
	public void setTipLabel() {
		if(popedNum>1) {
			int delta = popedNum*popedNum*5;
			this.tipLabel.setText(this.popedNum+"���� "+delta);
			//new Thread(new SuspendTipLabel(this)).start();
			
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
	/** ��ͣһ��ʱ���tipLabel��textֵ����Ϊ�� */
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
	/** ����firstCompletedֵ */
	public void addFirstCompleted() {
		this.firstCompleted++;
	}
}
