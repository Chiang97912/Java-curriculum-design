package popstar;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.FontUIResource;

import popstar.classicmode.GameLog;
import popstar.classicmode.MainFrame;
import popstar.timemode.TimeModeMainFrame;
/**
 * ��Ϸ�ϵ����˵�����
 * @author Peter
 *
 */
public class MenuView extends JFrame{
	/** ����Ϸ��ť */
	private JButton playButton;
	/** ������Ϸ��ť */
	private JButton continueButton;
	/** ��ʱģʽ��ť */
	private JButton timeModeButton;
	/** ��Ϸ������ť */
	private JButton helpButton;
	/** �˳���Ϸ��ť */
	private JButton exitButton;
	/** �˵������ǩ��������ӱ���ͼƬ����ذ�ť */
	private JLabel label;
	/** ���ں�label��ǩ�Ŀ�� */
	private int width;
	/** ���ں�label��ǩ�ĸ߶� */
	private int height;
	/** �˵�����ı���ͼƬ */
	private String imagePath;
	/** ����ͼƬ���� */
	private ImageIcon im;
	/** ����ģʽ������� */
	private MainFrame mf;
	/** �˵����汾�� */
	private MenuView mv = this;
	/** ������Ч */
	public AudioClip sound;
	public Font font;
	MenuView() {
		label = new JLabel();
		loadImage();
		loadButton();
		this.setSize(width,height);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setVisible(true);

		this.addMouseMotionListener(new MouseEventListener(this));
		this.addMouseListener(new MouseEventListener(this));
		
		HighestScoreLabel hsl = new HighestScoreLabel(width);
		new Thread(hsl).start();
		this.getLayeredPane().add(hsl,Integer.MAX_VALUE);
		File file = new File("sounds/background.wav");
		try {
			sound = Applet.newAudioClip(file.toURL());
			sound.play();
			sound.loop();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.setIconImage(new ImageIcon("icons/pass_1.png").getImage());
	}
	/** �����϶��¼��ļ����� */
	class MouseEventListener implements MouseInputListener {
	     /** ��갴��ʱ������������� */
	     Point origin;
	     /** �����������Ĵ��� */
	     JFrame frame;
	     /** ���ڵ�ǰ���� */
	     Point p;
	     
	     public MouseEventListener(JFrame frame) {
	       this.frame = frame;
	       origin = new Point();
	     }
	     
	     @Override
	     public void mouseClicked(MouseEvent e) {
	    	 
	     }
	     
	     @Override
	     public void mousePressed(MouseEvent e) {}
	 
	     @Override
	     public void mouseReleased(MouseEvent e) {}
	
	     @Override
	     public void mouseEntered(MouseEvent e) {}
	     
	
	     @Override
	     public void mouseExited(MouseEvent e) {
	       this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	     }
	 
	
	     @Override
	     public void mouseDragged(MouseEvent e) {
	       p = this.frame.getLocation();
	       this.frame.setLocation(
	         p.x + (e.getX() - origin.x), 
	         p.y + (e.getY() - origin.y));
	     }
	 
	     @Override
	     public void mouseMoved(MouseEvent e) {
	    	 
	     }
	}
	
	/** ���ز˵�����Ĺ��ܰ�ť */
	private void loadButton() {
		FontClass fontClass = new FontClass("fonts/STCAIYUN.TTF",20f);
		font = fontClass.loadFont();
		playButton = new JButton();
		playButton.setBounds(150,150,120,30);
		playButton.setBackground(new Color(251,216,96));
		playButton.setText("����Ϸ");
		playButton.setFont(font);
		playButton.setBorderPainted(false);
		playButton.setFocusable(false);
		/**Ϊ����Ϸ��ť���һ������������*/
		playButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FileOutputStream fos = new FileOutputStream("game.log");
					fos.write(new byte[0]);
					fos.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				mf = new MainFrame(mv);
				mv.setVisible(false);
				sound.stop();
				File file = new File("sounds/readygo.wav");
				try {
					AudioClip sound = Applet.newAudioClip(file.toURL());
					sound.play();
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		continueButton = new JButton();
		continueButton.setText("����");
		continueButton.setFont(font);
		continueButton.setBorderPainted(false);
		continueButton.setFocusable(false);
		continueButton.setBackground(new Color(251,216,96));
		continueButton.setBounds(150,200,120,30);
		continueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameLog gameLog = null;
				try {
					FileInputStream fis = new FileInputStream("game.log");
					ObjectInputStream ois = new ObjectInputStream(fis);
					gameLog = (GameLog)ois.readObject();
					ois.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				} catch (ClassNotFoundException e3) {
					e3.printStackTrace();
				}
				mf = new MainFrame(mv);
				mf.setLevel(gameLog.getLevel());
				mf.setGoal(gameLog.getGoal());
				mf.setScore(gameLog.getScore());
				mf.setImageTagAll(gameLog.getImageTag());
				mf.setButtons(gameLog.getIsVisible());
				mf.setVisible(true);
				MenuView.this.setVisible(false);
				sound.stop();
			}
			
		});
		timeModeButton = new JButton("��ʱģʽ");
		timeModeButton.setFont(font);
		timeModeButton.setBounds(150,250,120,30);
		timeModeButton.setBackground(new Color(251,216,96));
		timeModeButton.setBorderPainted(false);
		timeModeButton.setFocusable(false);
		timeModeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new TimeModeMainFrame(MenuView.this);
				mv.setVisible(false);
				mv.sound.stop();
			}
			
		});
		
		helpButton = new JButton("��Ϸ����");
		helpButton.setFont(font);
		helpButton.setBounds(150,300,120,30);
		helpButton.setBackground(new Color(251,216,96));
		helpButton.setBorderPainted(false);
		helpButton.setFocusable(false);
		helpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HelpDialog helpDialog = new HelpDialog(MenuView.this);
				
			}
			
		});
		exitButton = new JButton("�˳���Ϸ");
		exitButton.setFont(font);
		exitButton.setBounds(150,350,120,30);
		exitButton.setBackground(new Color(251,216,96));
		exitButton.setBorderPainted(false);
		exitButton.setFocusable(false);
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		label.add(playButton);
		label.add(continueButton);
		label.add(timeModeButton);
		label.add(helpButton);
		label.add(exitButton);
		
	}
	/** ���ر���ͼƬ */
	private void loadImage() {
		imagePath = "icons/background.png";
		im = new ImageIcon(imagePath);
		label.setIcon(im);
		width = im.getIconWidth();
		height = im.getIconHeight();
		this.add(label);
	}
}
